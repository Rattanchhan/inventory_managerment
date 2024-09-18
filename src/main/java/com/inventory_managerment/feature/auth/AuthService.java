package com.inventory_managerment.feature.auth;

import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.auth.dto.RegisterResponse;
import com.inventory_managerment.feature.auth.dto.SendVerificationRequest;
import com.inventory_managerment.feature.auth.dto.VerificationRequest;

import jakarta.mail.MessagingException;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    String isVerification(String email) throws MessagingException;
    void verify(VerificationRequest verificationRequest);
    String resendVerification(SendVerificationRequest sendVerificationRequest);
}
