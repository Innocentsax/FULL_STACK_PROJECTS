package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.ENUM.UserType;
import com.decagon.eventhubbe.config.CloudinaryConfig;
import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.JwtToken;
import com.decagon.eventhubbe.domain.repository.AppUserRepository;
import com.decagon.eventhubbe.domain.repository.JwtTokenRepository;
import com.decagon.eventhubbe.dto.request.LoginRequest;
import com.decagon.eventhubbe.dto.request.RegistrationRequest;
import com.decagon.eventhubbe.dto.response.LoginResponse;
import com.decagon.eventhubbe.dto.response.RefreshTokenResponse;
import com.decagon.eventhubbe.dto.response.RegistrationResponse;
import com.decagon.eventhubbe.events.register.RegistrationEvent;
import com.decagon.eventhubbe.exception.AppUserAlreadyExistException;
import com.decagon.eventhubbe.exception.AppUserNotFoundException;
import com.decagon.eventhubbe.exception.InvalidCredentialsException;
import com.decagon.eventhubbe.exception.UserDisabledException;
import com.decagon.eventhubbe.security.JwtService;
import com.decagon.eventhubbe.service.AppUserService;
import com.decagon.eventhubbe.utils.DateUtils;
import com.decagon.eventhubbe.utils.EmailUtils;
import com.decagon.eventhubbe.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenRepository jwtTokenRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final ApplicationEventPublisher publisher;

    @Override
    public RegistrationResponse registerUser(RegistrationRequest registrationRequest, String usertype,
                                                    HttpServletRequest request) {
        validateUserExistence(registrationRequest.getEmail());
        AppUser appUser = registrationRequestToAppUser(registrationRequest);
        appUser.setDateOfBirth(DateUtils.saveDateOfBirth(registrationRequest.getDateOfBirth()));
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setImageUrl("http://res.cloudinary.com/dknryxg72/image/upload/c_fill,h_250,w_200/image_id6495085e37060874b1d34270");
        if(usertype.equals("eventGoer")){
            appUser.setUserType(UserType.EVENT_GOER);
        }else if (usertype.equals("eventCreator")){
            appUser.setUserType(UserType.EVENT_CREATOR);
        }
        appUser.setEnabled(false);
        AppUser savedUser = appUserRepository.insert(appUser);
        publisher.publishEvent(new RegistrationEvent(appUser, EmailUtils.frontEndAppUrl(request)));

        return RegistrationResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .message("Registration Successful")
                .build();
    }
    @Override
    public LoginResponse authenticate(LoginRequest loginRequest){
        AppUser appUser = getUserByEmail(loginRequest.getEmail());
        if(appUser.getEnabled().equals(false)){
            throw new UserDisabledException("Account is Disabled");
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())){
            throw new InvalidCredentialsException("Passwords do not match");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                appUser.getEmail(),appUser.getPassword());
        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(authentication);
        JwtToken jwtToken = JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .appUser(appUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        jwtService.revokeAllUserTokens(appUser);
        JwtToken savedToken = jwtTokenRepository.save(jwtToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return LoginResponse.builder()
                .accessToken(savedToken.getAccessToken())
                .refreshToken(savedToken.getRefreshToken())
                .userFullName(appUser.getFirstName()+" "+appUser.getLastName())
                .userEmail(appUser.getEmail())
                .imageUrl(appUser.getImageUrl())
                .message("Login Successful")
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response){
        String refreshToken;
        String username;
        String authHeader = request.getHeader("Authorization");

        if(authHeader!=null&&authHeader.startsWith("Bearer ")) {
            refreshToken = authHeader.substring(7);
            username = jwtService.extractUsername(refreshToken);
            JwtToken jwtToken = jwtTokenRepository.findByRefreshToken(refreshToken);
            if(username!=null) {
                var userDetails = appUserRepository.findByEmail(username)
                        .orElseThrow();
                UserDetails user = new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
                var isTokenValid = jwtTokenRepository.findJwtTokenByRefreshToken(refreshToken)
                        .map(t -> !t.isExpired() && !t.isRevoked())
                        .orElse(false);
                if (jwtService.isTokenValid(refreshToken, user) && isTokenValid) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails.getEmail(), user.getPassword());
                    var accessToken = jwtService.generateToken(authentication);
                    jwtToken.setAccessToken(accessToken);
                    jwtTokenRepository.save(jwtToken);
                    return RefreshTokenResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public String uploadProfilePicture(MultipartFile file) {
        AppUser user = appUserRepository.findByEmail(UserUtils.getUserEmailFromContext())
                .orElseThrow(()-> new AppUserNotFoundException("USER NOT FOUND"));
        CloudinaryConfig config = new CloudinaryConfig();
        String url =config.imageLink(file,user.getId());
        user.setImageUrl(url);
        appUserRepository.save(user);
        return url;
    }

    private AppUser registrationRequestToAppUser (RegistrationRequest registrationRequest) {
        return modelMapper.map(registrationRequest, AppUser.class);
    }
    private void validateUserExistence(String email){
        if(appUserRepository.existsByEmail(email)){
            throw new AppUserAlreadyExistException(email);
        }
    }
    public AppUser getUserByEmail(String email){
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new AppUserNotFoundException("User Not Found"));
    }

}
