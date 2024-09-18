package com.inventory_managerment.mapper.user;
import com.inventory_managerment.domain.Role_Permission;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.permission.dto.PermissionResponse;
import com.inventory_managerment.feature.user.dto.UserRequest;
import com.inventory_managerment.feature.user.dto.UserResponse;
import com.inventory_managerment.feature.user.dto.UserUpdateRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper{
    User fromUserRequest(UserRequest userRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpdateRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    UserResponse toUserResponse(User user);

    @Mapping(target = "name",source = "permission.name")
    @Mapping(target = "code",source = "permission.code")
    @Mapping(target = "module",source = "permission.module")
    @Mapping(target = "id",source = "permission.id")
    PermissionResponse toPermissionResponse(Role_Permission role_Permission);

    List<UserResponse> toUserList(List<User> users);

    User fromRegisterRequest(RegisterRequest registerRequest);
}
