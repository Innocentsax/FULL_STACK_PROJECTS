package com.goCash.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goCash.dto.request.ChangePasswordRequestDto;
import com.goCash.dto.request.LoginRequest;
import com.goCash.dto.request.UserRegistrationRequest;
import com.goCash.dto.response.FlutterWaveAccountResponse;
import com.goCash.dto.response.FlutterWaveErrorResponse;
import com.goCash.dto.response.LoginResponse;
import com.goCash.dto.response.UserResponse;
import com.goCash.entities.User;
import com.goCash.entities.WalletAccount;
import com.goCash.exception.UserNotFoundException;
import com.goCash.repository.UserRepository;
import com.goCash.repository.WalletRepository;
import com.goCash.security.JwtService;
import com.goCash.services.FlutterWaveService;
import com.goCash.services.UserService;
import com.goCash.utils.ApiResponse;
import com.goCash.utils.EntityMapper;
import com.goCash.utils.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final EntityMapper entityMapper;
    private final FlutterWaveService flutterWaveService;
    private final ObjectMapper objectMapper;
    private final Util util;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse<String> registerUser(UserRegistrationRequest request) {
        log.info("Check if the user already exists");
        boolean doesUserExist = userRepository.existsByEmail(request.getEmail());
        if (doesUserExist) {
            return new ApiResponse<>("01", "email already taken", HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<String> response = flutterWaveService.createVirtualAcccount(request);
        if (response.getStatusCode().is2xxSuccessful()) {
            FlutterWaveAccountResponse apiResponse;
            try {
                apiResponse = objectMapper.readValue(response.getBody(), FlutterWaveAccountResponse.class);
            } catch (JsonProcessingException e) {
                log.info(response.getBody());
                throw new RuntimeException(e);
            }

            log.info(" create the wallet");
            assert apiResponse != null;
            User newuser = entityMapper.dtoToUser(request);
            userRepository.save(newuser);
            WalletAccount newWallet = WalletAccount.builder()
                    .accountNumber(apiResponse.getData().getAccountNumber())
                    .balance(Double.parseDouble(apiResponse.getData().getAmount()))
                    .user(newuser)
                    .build();
            newWallet.setBalance(50000);
            walletRepository.save(newWallet);
            log.info("successully saved the wallet");

            return new ApiResponse<>("00", "User successfully created", HttpStatus.CREATED);


        } else {
            log.info("There is an error in the request.");
            try {
                FlutterWaveErrorResponse flutterWaveErrorResponse = objectMapper.readValue(response.getBody(), FlutterWaveErrorResponse.class);
                return ApiResponse.<String>builder()
                        .message(flutterWaveErrorResponse.getMessage())
                        .code("01")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

    }


    @Override
    public ApiResponse<LoginResponse> login(LoginRequest loginDTO) {
        log.info("Request to login at the service layer");

        Authentication authenticationUser;
        try {
            authenticationUser = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            log.info("Authenticated the User by the Authentication manager");
        } catch (DisabledException es) {
            return ApiResponse.<LoginResponse>builder()
                    .message("Disabled exception occurred")
                    .code("01")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("BadException " + e.getMessage());
        }

        // Tell securityContext that this user is in the context
        SecurityContextHolder.getContext().setAuthentication(authenticationUser);

        // Retrieve the user from the repository
        User appUser = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(() ->
                new UserNotFoundException("User not found"));

        // Update the lastLoginDate field
        appUser.setLastLoginDate(LocalDateTime.now());

        // Save the updated user entity
        User user = userRepository.save(appUser);

        // Generate and send token
        String tokenGenerated = "Bearer " + jwtService.generateToken(authenticationUser);
        log.info("Jwt token generated for the user");
        LoginResponse loginResponse = LoginResponse.builder().token(tokenGenerated)
                .email(loginDTO.getEmail()).firstName(user.getFirstName())
                .role(user.getRole())
                .lastName(user.getLastName()).build();

        return ApiResponse.<LoginResponse>builder()
                .message("Successfully logged in")
                .data(loginResponse)
                .code("00")
                .httpStatus(HttpStatus.OK)
                .build();
    }

@Override
    public ApiResponse<UserResponse> getUser(){
        String loggedInUserName = util.getLoginUser();
        log.info("check if user exists");
    Optional<User> user = userRepository.findByEmail(loggedInUserName);
    if (user.isEmpty()) {
        return new ApiResponse("01", "User does not exist", HttpStatus.BAD_REQUEST);
    }
    log.info("check for the wallet that belongs to the user");
    Optional<WalletAccount> walletAccount = walletRepository.findByUser(user.get());
    if (walletAccount.isEmpty()) {
        return new ApiResponse("01", "This wallet does not exist", HttpStatus.BAD_REQUEST);
    }
    log.info("user exist, create a new user");
    UserResponse userResponse = new UserResponse();
    userResponse.setFirstName(user.get().getFirstName());
    userResponse.setLastName(user.get().getLastName());
    userResponse.setAccountNumber(walletAccount.get().getAccountNumber());
    userResponse.setEmail(user.get().getEmail());
    return new ApiResponse("00","Successful",userResponse);
}

    @Override
    public ApiResponse<ChangePasswordRequestDto> changePassword(ChangePasswordRequestDto requestDto) {
        log.info("Request to change password at the service layer");

        // Find the user by their email
        String userEmail = util.getLoginUser();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Verify the user's current password
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            return ApiResponse.<ChangePasswordRequestDto>builder()
                    .code("01")
                    .message("Invalid current password")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        // Update the user's password to the new one
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);

        // Password change is successful
        return ApiResponse.<ChangePasswordRequestDto>builder()
                .code("00")
                .message("Password changed successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }
}
