package com.easyLend.userservice.services.serviceImpl;

import com.easyLend.userservice.domain.constant.UserType;
import com.easyLend.userservice.domain.entity.AppUser;
import com.easyLend.userservice.domain.entity.JwtToken;
import com.easyLend.userservice.domain.repository.AppUserRepository;
import com.easyLend.userservice.domain.repository.JwtTokenRepository;
import com.easyLend.userservice.event.RegisterEvent;
import com.easyLend.userservice.exceptions.PasswordNotFoundException;
import com.easyLend.userservice.exceptions.UserAlreadyExistExceptions;
import com.easyLend.userservice.exceptions.UserNotActivatedException;
import com.easyLend.userservice.request.LoginRequest;
import com.easyLend.userservice.request.RegisterRequest;
import com.easyLend.userservice.response.LoginResponse;
import com.easyLend.userservice.response.RegisterResponse;
import com.easyLend.userservice.response.UserResponse;
import com.easyLend.userservice.security.JwtService;
import com.easyLend.userservice.services.AppUserService;
import com.easyLend.userservice.utils.EmailUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final JwtTokenRepository jwtTokenRepository;

    @Value("${application.security.jwt.expiration}")
    private long expirationTime;

    public AppUser confirmUserExists(String email){
        return appUserRepository.findAppUserByEmail(email).orElseThrow(()-> new UserAlreadyExistExceptions("User Not Found"));
    }
    private void confirmUser(String email){
        Boolean appUser = appUserRepository.existsAppUserByEmail(email);
        if (appUser){
            throw new UserAlreadyExistExceptions("User Already Exist");
        }
    }
    @Override
    public RegisterResponse registerUser(RegisterRequest request, UserType userType, HttpServletRequest httpServletRequest) {
        confirmUser(request.getEmail());
            AppUser appUser = appUserRepository.save(saveUserDTO(request));
//
            publisher.publishEvent(new RegisterEvent(appUser, EmailUtils.ReactUrl(httpServletRequest)));
            return modelMapper.map(appUser,RegisterResponse.class);
    }

    @Override
    public LoginResponse loginAuth(LoginRequest loginRequest) {
        AppUser user = confirmUserExists(loginRequest.getEmail());

            if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
                throw new PasswordNotFoundException("Password does not match");
            }
        if(user.getRegistrationStatus()){
            JwtToken token = jwtTokenRepository.findByUser(user);
            if (token != null) {
                System.out.println(token.getAccessToken());
                jwtTokenRepository.delete(token);
            }
            String jwt = jwtService.generateToken(user,user.getUserId(),user.getUserType());
            String refresh = jwtService.generateRefreshToken(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),user.getPassword());

            JwtToken jwtToken = JwtToken.builder()
                    .accessToken(jwt)
                    .refreshToken(refresh)
                    .user(user)
                    .generatedAt(new Date(System.currentTimeMillis()))
                    .expiresAt(new Date(System.currentTimeMillis() + expirationTime))
                    .build();

            JwtToken savedToken = jwtTokenRepository.save(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return LoginResponse.builder()
                    .activate(savedToken.getUser().getRegistrationStatus())
                    .accessToken(savedToken.getAccessToken())
                    .refreshToken(savedToken.getRefreshToken())
                    .username(user.getFullName())
                    .registrationStatus(user.getRegistrationStatus())
                    .registrationStage(user.getRegistrationStage())
                    .email(savedToken.getUser().getEmail())
                    .build();
        }
        throw new UserNotActivatedException("User Not Activated");
    }


    @Override
    public List<UserResponse> listOfUsers() {
        List<AppUser> users = appUserRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }




    private AppUser saveUserDTO(RegisterRequest request){
        return AppUser.builder()
                .userType(UserType.BORROWER)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .registrationStatus(false)
                .image("https://res.cloudinary.com/jwsven/image/upload/v1690722516/319700901_1156202005084909_3853009742472973832_n_lmuvqu.jpg")
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }


}
