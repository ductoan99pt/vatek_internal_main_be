package asia.vatek.project.controller;

import asia.vatek.project.constant.ErrorConstant;
import asia.vatek.project.dto.ResponseDto;
import asia.vatek.project.dto.user.UserDto;
import asia.vatek.project.readable.form.updateForm.UpdateUserForm;
import asia.vatek.project.service.RefreshTokenService;
import asia.vatek.project.service.UserService;
import asia.vatek.project.jwt.JwtProvider;
import asia.vatek.project.readable.request.ChangePasswordReq;
import asia.vatek.project.readable.request.ChangeStatusAccountReq;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    final
    PasswordEncoder encoder;

    final
    UserService userService;

    final
    JavaMailSender emailSender;

    final
    JwtProvider jwtProvider;

    final
    RefreshTokenService refreshTokenService;

    @PutMapping(value = "/activateEmail/{id}")
    public ResponseDto<Boolean> activateEmail(@PathVariable Long id){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();

        Instant instant = Instant.now();

        responseDto.setContent(userService.activateEmail(id,instant));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/changePassword")
    public  ResponseDto<Boolean> changePassword(@Valid @RequestBody ChangePasswordReq changePasswordReq){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changePassword(changePasswordReq));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/changeStatus")
    public  ResponseDto<Boolean> changeStatus(@Valid @RequestBody ChangeStatusAccountReq changeStatusAccountReq){
        ResponseDto<Boolean> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.changeStatus(changeStatusAccountReq));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }

    @PutMapping(value = "/updateUser")
    public ResponseDto<UserDto> updateUser(
            @RequestBody UpdateUserForm form
    ) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        responseDto.setContent(userService.updateUser(form));
        responseDto.setErrorCode(ErrorConstant.Code.SUCCESS);
        responseDto.setMessage(ErrorConstant.Message.SUCCESS);
        responseDto.setErrorType(ErrorConstant.Type.SUCCESS);
        return responseDto;
    }


}
