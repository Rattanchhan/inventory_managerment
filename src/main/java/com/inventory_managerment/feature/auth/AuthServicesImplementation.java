package com.inventory_managerment.feature.auth;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.TemplateEngine;
import com.inventory_managerment.domain.Role;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.domain.UserVerification;
import com.inventory_managerment.feature.auth.dto.AuthResponse;
import com.inventory_managerment.feature.auth.dto.LoginRequest;
import com.inventory_managerment.feature.auth.dto.RefreshTokenRequest;
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
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.time.*;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final JwtEncoder refreshTokenJwtEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

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

        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        Instant now = Instant.now();

        // Create AccessToken
        String accessToken =  createToken(accessTokenJwtEncoder, createClaimsSet(authentication, now, scope, null,"Access APIs"));
        
        // Create RefreshToken
        String refreshToken = createToken(refreshTokenJwtEncoder, createClaimsSet(authentication, now, scope, null,"Refresh Token"));

        return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refeshToken(refreshToken)
                            .tokenType("Bearer")
                            .build();
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String accessToken;
        String refreshToken= refreshTokenRequest.refreshToken();
        Authentication authentication = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());
        authentication=jwtAuthenticationProvider.authenticate(authentication);

        Jwt jwt = (Jwt)authentication.getPrincipal();
        Instant now = Instant.now();
        
        Instant expiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, expiresAt).toDays();

        log.info("{}",remainingDays);

        if(remainingDays<=1){
            refreshToken=createToken(accessTokenJwtEncoder, createClaimsSet(authentication, now, null, jwt,"Access APIs"));
        }

        accessToken = createToken(accessTokenJwtEncoder, createClaimsSet(authentication, now, null, jwt,"Access APIs"));
        return AuthResponse.builder()
                            .tokenType("Bearer")
                            .accessToken(accessToken)
                            .refeshToken(refreshToken)
                            .build();
    }

    public String createToken(JwtEncoder tokentJwtEncoder,JwtClaimsSet jwtClaimsSet){
        return  tokentJwtEncoder
                .encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }

    public JwtClaimsSet createClaimsSet(Authentication authentication,Instant now,String scope,Jwt jwt,String subject){
        
        Instant expired=now.plus(30,ChronoUnit.MINUTES);

        if(jwt!=null){
            return JwtClaimsSet.builder()
                    .id(jwt.getId())
                    .subject(subject)
                    .issuer(jwt.getId())
                    .issuedAt(now)
                    .expiresAt(now.plus(30,ChronoUnit.DAYS))
                    .audience(jwt.getAudience())
                    .claim("isAdmin", true)
                    .claim("studentId","ISTAD0010")
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();
        }
        else{
            if(subject=="Refresh Token"){
                expired= now.plus(30,ChronoUnit.DAYS);
            }
            return JwtClaimsSet.builder()
                    .id(authentication.getName())
                    .subject(subject)
                    .issuer(authentication.getName())
                    .issuedAt(now)
                    .expiresAt(expired)
                    .audience(List.of("NextJS","Android","IOS"))
                    .claim("isAdmin", true)
                    .claim("studentId","ISTAD0010")
                    .claim("scope", scope)
                    .build();
        }
    }
    
}
