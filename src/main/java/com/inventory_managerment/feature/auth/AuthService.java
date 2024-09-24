package com.inventory_managerment.feature.auth;

import com.inventory_managerment.feature.auth.dto.AuthResponse;
import com.inventory_managerment.feature.auth.dto.LoginRequest;
import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.auth.dto.RegisterResponse;
import com.inventory_managerment.feature.auth.dto.SendVerificationRequest;
import com.inventory_managerment.feature.auth.dto.VerificationRequest;

import jakarta.mail.MessagingException;

public interface AuthService {
    AuthResponse login(LoginRequest LoginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
    String sendVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
    String resendVerification(SendVerificationRequest sendVerificationRequest);
}
