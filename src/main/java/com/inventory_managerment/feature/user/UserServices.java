package com.inventory_managerment.feature.user;
import com.inventory_managerment.feature.user.dto.UserRequest;
import com.inventory_managerment.feature.user.dto.UserResponse;
import com.inventory_managerment.feature.user.dto.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
public interface UserServices {
    UserResponse getUserById(Long  id);
    Page<UserResponse> getUsers(int page, int size);
    UserResponse addUser(UserRequest userRequest) throws Exception;
    UserResponse updateUser(Long  id, UserUpdateRequest userUpdateRequest);
    ResponseEntity<?> deleteUser(Long id);
}
