package com.inventory_managerment.feature.auth;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inventory_managerment.feature.auth.dto.AuthResponse;
import com.inventory_managerment.feature.auth.dto.LoginRequest;
import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.auth.dto.RegisterResponse;
import com.inventory_managerment.feature.auth.dto.SendVerificationRequest;
import com.inventory_managerment.feature.auth.dto.VerificationRequest;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse Login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest){
        return authService.register(registerRequest);
    }

    @PostMapping("/send-verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String verification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException{
        return authService.sendVerification(sendVerificationRequest.email());
    }

    @PostMapping("/resend-verification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String resendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException{
        return authService.resendVerification(sendVerificationRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/verify")
    public void verify(@Valid @RequestBody VerificationRequest verificationRequest) throws MessagingException{
        authService.verify(verificationRequest);
    }
}
