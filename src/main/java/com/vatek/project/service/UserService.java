package com.vatek.project.service;



import com.vatek.project.dto.ListResponseDto;
import com.vatek.project.dto.user.UserDto;
import com.vatek.project.entity.UserEntity;
import com.vatek.project.readable.form.createForm.CreateUserForm;
import com.vatek.project.readable.form.updateForm.UpdateUserForm;
import com.vatek.project.readable.request.ChangePasswordReq;
import com.vatek.project.readable.request.ChangeStatusAccountReq;

import java.time.Instant;

public interface UserService {
    UserEntity saveToken(String token, UserEntity userEntity);

    UserEntity clearToken(UserEntity userEntity);

    void clearAllToken();

    UserEntity findUserEntityByEmailForLogin(String email);

    UserEntity findUserEntityByEmail(String email);

    ListResponseDto<UserDto> getUserList(
            int pageIndex,
            int pageSize
    );
    UserDto createUser(CreateUserForm form);

    Boolean activateEmail(Long id, Instant timeOut);

    Boolean forgotPassword(String email);

    Boolean changePassword(ChangePasswordReq changePasswordReq);

    Boolean changeStatus(ChangeStatusAccountReq changeStatusAccountReq);

    UserDto updateUser(UpdateUserForm form);
}
