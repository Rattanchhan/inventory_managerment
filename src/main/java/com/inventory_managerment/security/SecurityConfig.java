package com.inventory_managerment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

   /*@Bean
   InMemoryUserDetailsManager configureUserSecurity(){

        UserDetails admin = User.withUsername( "admin")
                                .password(passwordEncoder.encode("123"))
                                .roles( "USER","ADMIN").build();
 
        UserDetails editor = User.withUsername("editor")
                                .password(passwordEncoder.encode("123"))
                                .roles("USER","EDITOR").build();

        UserDetails customer = User.withUsername("customer")
                                .password(passwordEncoder.encode("123"))
                                .roles("USER","CUSTOMER").build();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(admin);
        manager.createUser(editor);
        manager.createUser(customer);

       return manager;
    }
*/

    @Bean
    DaoAuthenticationProvider configureDaoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configureApiSecurity(HttpSecurity httpSecurity) throws Exception{
        // Security Mechanism (HTTP Basic Auth)
        // HTTP Basic Auth (Username and Password)

        httpSecurity.authorizeHttpRequests(endpoint-> endpoint.
        requestMatchers(HttpMethod.POST,"api/v1/users/**")
        .hasRole("Administrator")

        .requestMatchers(HttpMethod.GET,"api/v1/users/**")
        .hasRole("Administrator")

        .requestMatchers(HttpMethod.DELETE,"api/v1/users/**")  
        .hasAnyRole("ADMIN")
        .anyRequest().authenticated());


        httpSecurity.httpBasic(Customizer.withDefaults());

        //Disable csrf token 
        httpSecurity.csrf(token -> token.disable());


        // Make Stateless Session

        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return httpSecurity.build();
    }
    
}
