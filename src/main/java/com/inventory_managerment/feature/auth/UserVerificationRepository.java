package com.inventory_managerment.feature.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory_managerment.domain.User;
import com.inventory_managerment.domain.UserVerification;
import java.util.Optional;

public interface UserVerificationRepository extends JpaRepository<UserVerification,Long> {
    
    Optional<UserVerification> findByUserAndVerifiedCode(User user,String verifiedCode);
    Optional<UserVerification> findByUser(User user);
}
