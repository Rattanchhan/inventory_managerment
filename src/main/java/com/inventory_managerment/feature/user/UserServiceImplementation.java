package com.inventory_managerment.feature.user;
import com.inventory_managerment.domain.Role;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.feature.role.dto.RoleRepository;
import com.inventory_managerment.feature.user.dto.UserRequest;
import com.inventory_managerment.feature.user.dto.UserResponse;
import com.inventory_managerment.feature.user.dto.UserUpdateRequest;
import com.inventory_managerment.mapper.user.UserMapper;
import com.inventory_managerment.security.HashingPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImplementation implements UserServices{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    // private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse getUserById(Long id) {
        User user= userRepository
                .getUserById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public Page<UserResponse> getUsers(int page, int size) {
        Sort sortName = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sortName);
        Page<User> users = userRepository.getAllByPage(pageRequest);
        return users.map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) throws Exception {
        User  user = userMapper.fromUserRequest(userRequest);
        User getUser = userRepository.findById(userRequest.userId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found")
        );
        Role role = roleRepository.findById(userRequest.roleId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Role does not exist")
        );

        user.setPassword(String.valueOf(HashingPassword.hashPassword(user.getPassword())));
        user.setRole(role);
        //Set by system
        user.setStatus("active");
        user.setUser(getUser);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user= userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found"));
        user.setUpdatedAt(new Date());
        userMapper.fromUserUpdateRequest(userUpdateRequest,user);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("Deleted",Boolean.TRUE));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found");
    }
}
