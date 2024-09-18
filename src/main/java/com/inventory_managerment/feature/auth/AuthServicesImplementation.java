package com.inventory_managerment.feature.auth;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.inventory_managerment.domain.Role;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.auth.dto.RegisterResponse;
import com.inventory_managerment.feature.role.dto.RoleRepository;
import com.inventory_managerment.feature.user.UserRepository;
import com.inventory_managerment.mapper.user.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServicesImplementation  implements AuthService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        // Validate Phone Number
        if(userRepository.existsByPhoneNumber(registerRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Phone Number has been already existing...");
        }

        // Validate Email
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Email has beean already existing...");
        }

        // Validate user's Password
        if(!registerRequest.confirmPassword().equals(registerRequest.password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "Password has not been match");
        }

        Role role = roleRepository.findRoleUser();

        User user = userMapper.fromRegisterRequest(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        userRepository.save(user);

        return RegisterResponse.builder()
        .message("You register has been successfully")
        .email(user.getEmail()).build();
    }
    
}
