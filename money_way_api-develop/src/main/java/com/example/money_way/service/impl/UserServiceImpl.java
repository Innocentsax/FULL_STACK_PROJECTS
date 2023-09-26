package com.example.money_way.service.impl;

import com.example.money_way.configuration.mail.EmailService;
import com.example.money_way.configuration.security.CustomUserDetailService;
import com.example.money_way.configuration.security.JwtUtils;
import com.example.money_way.dto.request.*;
import com.example.money_way.dto.response.ApiResponse;
import com.example.money_way.dto.response.UserProfileResponse;
import com.example.money_way.enums.Role;
import com.example.money_way.exception.InvalidCredentialsException;
import com.example.money_way.exception.UserNotFoundException;
import com.example.money_way.exception.ValidationException;
import com.example.money_way.model.User;
import com.example.money_way.repository.UserRepository;
import com.example.money_way.repository.WalletRepository;
import com.example.money_way.service.UserService;
import com.example.money_way.service.WalletService;
import com.example.money_way.utils.AppUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUtil appUtil;
    private final EmailService emailService;
    private final WalletService walletService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final JwtUtils jwtUtils;
    private final WalletRepository walletRepository;



    @Override
    public ApiResponse<String> updatePassword(PasswordResetDTO passwordResetDTO) {

        String currentPassword = passwordResetDTO.getCurrentPassword();
        String newPassword = passwordResetDTO.getNewPassword();

        User user = appUtil.getLoggedInUser();

        String savedPassword = user.getPassword();

        if(!passwordEncoder.matches(currentPassword, savedPassword))
            throw new InvalidCredentialsException("Credentials must match");

        else
            passwordResetDTO.setNewPassword(newPassword);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Update Password", "Your password has been updated  successfully. Ensure to keep it a secret. Never disclose your password to a third party.");
        return new ApiResponse<>( "Success", "Password reset successful", null);

    }
    
    @Override
    public ResponseEntity<String> login(LoginRequestDto request) {

        User users = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new UserNotFoundException("User Not Found"));
        if(!users.isActive()) {
         throw new ValidationException(" User Not Active");
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = customUserDetailService.loadUserByUsername(request.getEmail());

        if (user != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(user));
        }
        return ResponseEntity.status(400).body("Some Error Occurred");
    }

    @Override
    public ApiResponse verifyLink(VerifyTokenDto verifyTokenDto) {

        Optional<User> existingUser = userRepository.findByConfirmationToken(verifyTokenDto.getToken());
        if (existingUser.isPresent()) {
            if (existingUser.get().isActive() && walletRepository.findByUserId(existingUser.get().getId()).isPresent()){
                return ApiResponse.builder().message("Account already verified").status("false").build();
            }
            existingUser.get().setConfirmationToken(null);
            existingUser.get().setActive(true);
            CreateWalletRequest request = new CreateWalletRequest();
            request.setEmail(existingUser.get().getEmail());
            request.setBvn(existingUser.get().getBvn());
            request.setIs_permanent(true);
            request.setTx_ref("TX"+appUtil.generateReference());
            walletService.createWallet(request);
            userRepository.save(existingUser.get());
            return ApiResponse.builder().message("Success").status("Account created successfully").build();
        }
        throw new UserNotFoundException("Error: No Account found! or Invalid Token");
    }


    @Override
    public ResponseEntity<ApiResponse> signUp(SignUpDto signUpDto) throws ValidationException {

        Boolean isUserExist = userRepository.existsByEmail(signUpDto.getEmail());
        if (isUserExist)
            throw new ValidationException("User Already Exists!");

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPhoneNumber(signUpDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setBvn(signUpDto.getBvn());
        user.setRole(Role.ROLE_USER);
        user.setPin(passwordEncoder.encode(signUpDto.getPin()));
        String token = jwtUtils.generateSignUpConfirmationToken(signUpDto.getEmail());
        user.setConfirmationToken(token);
        userRepository.save(user);

        String URL = "http://localhost:8080/api/v1/auth/verify-link/?token=" + token;
        String link = "<h3>Hello "  + signUpDto.getFirstName()  +"<br> Click the link below to activate your account <a href=" + URL + "><br>Activate</a></h3>";

        emailService.sendEmail(signUpDto.getEmail(),"MoneyWay: Verify Your Account", link);

        return ResponseEntity.ok(new ApiResponse<>("Successful", "SignUp Successful. Check your mail to activate your account", null));
    }

    @Override
    public ApiResponse<UserProfileResponse> getUserProfile() {
        User user = appUtil.getLoggedInUser();

        UserProfileResponse response = UserProfileResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .imageUrl(user.getImageUrl())
                .address(user.getAddress())
                .build();

        return new ApiResponse<>("SUCCESS", "User Profile", response);
    }

    @Override
    public ApiResponse<String> forgotPassword(ForgotPasswordDTORequest forgotPasswordDTORequest) throws IOException {
        String email = forgotPasswordDTORequest.getEmail();

        Boolean isEmailExist = userRepository.existsByEmail(email);
        if (!isEmailExist)
            throw new UserNotFoundException("User Does Not Exist!");

        User user = userRepository.findByEmail(email).get();
        String token = jwtUtils.resetPasswordToken(email);
        user.setConfirmationToken(token);
        userRepository.save(user);

        String resetPasswordLink = "http://localhost:8080/api/v1/auth/resetPassword" + token;
        String resetLink = "<h3>Hello " + user.getFirstName() + ",<br> Click the link below to reset your password <a href=" + resetPasswordLink + "><br>Reset Password</a></h3>";

        emailService.sendEmail(email, "MoneyWay: Click on the link to reset your Password", resetLink);
        return new ApiResponse<>(null, "A password reset link has been sent to your email", null);

    }
    @Override
    public ApiResponse<String> resetPassword(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        String password = resetPasswordRequestDTO.getNewPassword();
        User user = userRepository.findByConfirmationToken(resetPasswordRequestDTO.getToken()).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return new ApiResponse<String>("Success", "password reset successful", null);
    }

}
