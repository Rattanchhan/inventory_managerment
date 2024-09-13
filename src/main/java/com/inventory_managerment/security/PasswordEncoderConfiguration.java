package com.inventory_managerment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderConfiguration {
   @Bean
   public PasswordEncoder configurationPasswordEncoder() {
       return new BCryptPasswordEncoder();
   }

}
