package com.easyLend.userservice.security;


import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.repository.AppUserRepository;
import com.easyLend.userservice.exceptions.AppUserNotFoundExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByEmail(email)
                .orElseThrow(()->new AppUserNotFoundExceptions(email));
        return new AppUser(appUser.getEmail(),appUser.getPassword());
    }


}
