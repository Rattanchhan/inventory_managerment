package com.inventory_managerment.security;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.inventory_managerment.domain.User;
import com.inventory_managerment.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailServiceImplementation implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository
        .findByPhoneNumber(phoneNumber)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User has not been found..." ));

        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUser(user);

        for(GrantedAuthority grantedAuthority:customUserDetail.getAuthorities()){
            log.info("{}",grantedAuthority.getAuthority());
        }

        return customUserDetail;
    }
    
}
