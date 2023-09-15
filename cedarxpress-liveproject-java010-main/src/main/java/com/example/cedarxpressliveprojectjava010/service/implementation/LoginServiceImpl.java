package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.config.jwt.JwtTokenProvider;
import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.exception.IncorrectPasswordException;
import com.example.cedarxpressliveprojectjava010.service.BlacklistService;
import com.example.cedarxpressliveprojectjava010.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletResponse httpServletResponse;
    private final HttpServletRequest httpServletRequest;
    private final BlacklistService blacklistService;

    @Override
    public Authentication login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        log.info("Initiating authentication for " + email);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticatedToken = authenticationManager.authenticate(authToken);

        if (!authenticatedToken.isAuthenticated()) {
            log.error(email + " Inputted an incorrect password!");
            String INCORRECT_PASSWORD_MESSAGE = "The password you inputted is incorrect!";
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_MESSAGE);
        }

        SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

        log.info("Successfully logged in {}!", email );
        return authenticatedToken;
    }

    public String setUpJWT(Authentication authentication){
        log.info("Setting up JWT");
        String jwToken = jwtTokenProvider.generateToken(authentication);
        httpServletResponse.addHeader("Authorization", jwToken);
        log.info("JWT Created and stored in header");
        return jwToken;
    }

    @Override
    public void logOut(){
        log.info("Logging out!");
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        Date expiryDate = jwtTokenProvider.getExpiryDate(token);
        blacklistService.blackListToken(token, expiryDate);
        SecurityContextHolder.clearContext();
        httpServletResponse.reset();
    }
}

