package asia.vatek.project.controller;

import asia.vatek.project.constant.ErrorConstant;
import asia.vatek.project.dto.ResponseDto;
import asia.vatek.project.dto.user.UserDto;
import asia.vatek.project.entity.RefreshTokenEntity;
import asia.vatek.project.entity.UserEntity;
import asia.vatek.project.jwt.exception.ErrorResponse;
import asia.vatek.project.jwt.exception.TokenRefreshException;
import asia.vatek.project.readable.form.LoginForm;
import asia.vatek.project.readable.form.createForm.CreateUserForm;
import asia.vatek.project.security.service.UserPrinciple;
import asia.vatek.project.service.MailService;
import asia.vatek.project.service.RefreshTokenService;
import asia.vatek.project.service.UserService;
import asia.vatek.project.jwt.exception.ProductException;
import asia.vatek.project.jwt.JwtProvider;
import asia.vatek.project.jwt.JwtResponse;
import asia.vatek.project.jwt.payload.request.TokenRefreshRequest;
import asia.vatek.project.jwt.payload.response.TokenRefreshResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/auth")
public class AuthRestController {

    final
    AuthenticationManager authenticationManager;

    final
    MailService mailService;

    final
    UserService userService;

    final
    PasswordEncoder encoder;

    final
    JwtProvider jwtProvider;

    final
    ModelMapper modelMapper;

    final
    RefreshTokenService refreshTokenService;


    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@Valid @RequestBody CreateUserForm createUserForm) {
        UserDto createUserDto = userService.createUser(createUserForm);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(createUserDto);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/login")
    public ResponseDto<?> authenticateUser(@RequestBody LoginForm loginForm) {

        ResponseDto<JwtResponse> responseDto = new ResponseDto<>();

        UserEntity user = userService.findUserEntityByEmailForLogin(loginForm.getEmail());

        if(!encoder.matches(loginForm.getPassword(),user.getPassword())){
           throw new ProductException(new ErrorResponse(
                   ErrorConstant.Code.LOGIN_INVALID,
                   ErrorConstant.Type.LOGIN_INVALID,
                   ErrorConstant.Message.LOGIN_INVALID
           ));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getEmail(),
                        loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        String jwt = jwtProvider.generateJwtToken(authentication);

        user.setAccessToken(jwt);
        user.setTokenStatus(true);

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        List<String> roles = userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);

        responseDto.setContent(
                new JwtResponse(
                        jwt,
                        refreshToken.getToken(),
                        userPrinciple.getId(),
                        userPrinciple.getUsername(),
                        userPrinciple.getEmail(),
                        roles
                )
        );
        responseDto.setRemainTime(jwtProvider.getRemainTimeFromJwtToken(jwt));

        return responseDto;
    }

    @SneakyThrows
    @PostMapping("/forgotPassword")
    public ResponseDto<?> forgotPassword(@RequestParam(name = "email",defaultValue = "") String email) {

        if (StringUtils.isBlank(email)) {
            log.error("parameter email empty => {}",() -> email);
            throw new ProductException(
                    new ErrorResponse()
            );
        }

        userService.forgotPassword(email);

        ResponseDto<?> responseDto = new ResponseDto<>();
        responseDto.setRemainTime(0L);
        responseDto.setMessage(ErrorConstant.Code.SUCCESS);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        return responseDto;
    }

    @PostMapping("/refreshToken")
    public ResponseDto<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUserEntity)
                .map(user -> {
                    String token = jwtProvider.generateTokenFromEmail(user.getEmail());
                    userService.saveToken(token,user);
                    Long timeRemain = jwtProvider.getRemainTimeFromJwtToken(token);
                    ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
                    responseDto.setRemainTime(timeRemain);
                    responseDto.setMessage(ErrorConstant.Message.SUCCESS);
                    responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
                    responseDto.setContent(new TokenRefreshResponse(token, requestRefreshToken));
                    return responseDto;
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }



    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ProductException(
                    new ErrorResponse());
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        UserEntity userEntity = userService.findUserEntityByEmail(userPrinciple.getEmail());

        userService.clearToken(userEntity);

        new SecurityContextLogoutHandler().logout(request,response,authentication);

        ResponseDto<TokenRefreshResponse> responseDto = new ResponseDto<>();
        responseDto.setRemainTime(0L);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        return responseDto;

    }
}
