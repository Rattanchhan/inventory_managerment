package com.inventory_managerment.feature.auth;
import java.util.Date;

import org.hibernate.type.descriptor.jdbc.InstantAsTimestampJdbcType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import com.inventory_managerment.domain.Role;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.domain.UserVerification;
import com.inventory_managerment.feature.auth.dto.AuthResponse;
import com.inventory_managerment.feature.auth.dto.LoginRequest;
import com.inventory_managerment.feature.auth.dto.RegisterRequest;
import com.inventory_managerment.feature.auth.dto.RegisterResponse;
import com.inventory_managerment.feature.auth.dto.SendVerificationRequest;
import com.inventory_managerment.feature.auth.dto.VerificationRequest;
import com.inventory_managerment.feature.role.dto.RoleRepository;
import com.inventory_managerment.feature.user.UserRepository;
import com.inventory_managerment.mapper.user.UserMapper;
import com.inventory_managerment.util.RandomUntil;
import org.thymeleaf.context.Context;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.HashMap;
import java.time.*;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthServicesImplementation  implements AuthService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;

    @Value("${spring.mail.username}")
    private String adminEmail;

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

    @Override
    public String sendVerification(String email) throws MessagingException{

        //Validate email
        User user = userRepository.findByEmail(email)
                    .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User has not been found...")
                    );

        String codeRandom= RandomUntil.random6Digits();
        UserVerification userVerification = new UserVerification();
        userVerification.setUser(user);
        userVerification.setVerifiedCode(codeRandom);
        userVerification.setExpiryTime(LocalTime.now().plusSeconds(60));
 
        userVerificationRepository.save(userVerification);

        return prepareTemplateMail(email, user, codeRandom);

    }
    
    public String prepareTemplateMail(String email,User user,String codeRandom){

        Map<String,Object> templateModel= new HashMap<String, Object>();
        Context context = new Context();
        try {

            // Prepare model data for template
            templateModel.put("userName", user.getName());
            templateModel.put("userEmail", user.getEmail());
            templateModel.put("registrationDate", new Date());
            templateModel.put("code",codeRandom);
            context.setVariables(templateModel);

            // Process Thymeleaf template
            String htmlContent = templateEngine.process("email-template", context);

            // Prepare email for sending
            prepareMailSend(email, htmlContent);

            return "Email sent!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending email!";
        }
    }

    public void prepareMailSend(String toMail,String htmlContent) throws MessagingException{

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true);

        mimeMessageHelper.setFrom(adminEmail);
        mimeMessageHelper.setTo(toMail);
        mimeMessageHelper.setSubject("User Verification");
        mimeMessageHelper.setText(htmlContent,true);
        javaMailSender.send(message);
    }

    @Override
    public void verify(VerificationRequest verificationRequest) {

          //Validate email
          User user = userRepository.findByEmail(verificationRequest.email())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User has not been found...")
          );

          //Validate verified code
          UserVerification verification = userVerificationRepository.findByUserAndVerifiedCode(user,verificationRequest.verifiedCode())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User verifed not been found...")
          );

          //Is verified code expired
          if(LocalTime.now().isAfter(verification.getExpiryTime())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Verified code has expired");
          }

          user.setIsVerification(true);
          userRepository.save(user);

          userVerificationRepository.delete(verification);

    }

    @Override
    public String resendVerification(SendVerificationRequest sendVerificationRequest) {
          //Validate email
          User user = userRepository.findByEmail(sendVerificationRequest.email())
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User has not been found...")
          );

          //Validate verified code
          UserVerification verification = userVerificationRepository.findByUser(user)
          .orElseThrow(
              ()-> new ResponseStatusException(HttpStatus.NOT_FOUND ,"User verified not been found...")
          );

          String codeRandom= RandomUntil.random6Digits();

          verification.setVerifiedCode(codeRandom);
          verification.setExpiryTime(LocalTime.now().plusSeconds(60));
          userVerificationRepository.save(verification);

          return prepareTemplateMail(user.getEmail(), user, codeRandom);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication= new UsernamePasswordAuthenticationToken(loginRequest.phoneNumber(),loginRequest.password());
        authentication=daoAuthenticationProvider.authenticate(authentication);

        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                                    .id(authentication.getName())
                                    .subject("Access APIs")
                                    .issuer(authentication.getName())
                                    .issuedAt(now)
                                    .expiresAt(now.plus(30,ChronoUnit.MINUTES))
                                    .audience(List.of("NextJS","Android","IOS"))
                                    .claim("isAdmin", true)
                                    .claim("studentId","ISTAD0010")
                                    .build();

        String accessToken = accessTokenJwtEncoder
                            .encode(JwtEncoderParameters.from(jwtClaimsSet))
                            .getTokenValue();


        return AuthResponse.builder()
                            .accessToken(accessToken)
                            .tokenType("Bearer")
                            .build();
    }
    
}
