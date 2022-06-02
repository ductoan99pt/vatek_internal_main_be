package asia.vatek.project.service;



import asia.vatek.project.dto.ListResponseDto;
import asia.vatek.project.dto.user.UserDto;
import asia.vatek.project.readable.form.createForm.CreateUserForm;
import asia.vatek.project.readable.form.updateForm.UpdateUserForm;
import asia.vatek.project.entity.UserEntity;
import asia.vatek.project.readable.request.ChangePasswordReq;
import asia.vatek.project.readable.request.ChangeStatusAccountReq;

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
