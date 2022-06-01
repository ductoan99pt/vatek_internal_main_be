package com.vatek.project.controller;

import com.vatek.project.constant.ErrorConstant;
import com.vatek.project.dto.ResponseDto;
import com.vatek.project.dto.user.UserDto;
import com.vatek.project.jwt.JwtProvider;
import com.vatek.project.readable.form.updateForm.UpdateUserForm;
import com.vatek.project.readable.request.ChangePasswordReq;
import com.vatek.project.readable.request.ChangeStatusAccountReq;
import com.vatek.project.service.RefreshTokenService;
import com.vatek.project.service.UserService;

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
    public  ResponseDto<Boolean> activateEmail(@PathVariable Long id){
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
