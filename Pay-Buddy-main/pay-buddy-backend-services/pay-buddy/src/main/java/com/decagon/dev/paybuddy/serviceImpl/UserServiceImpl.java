package com.decagon.dev.paybuddy.serviceImpl;

import com.decagon.dev.paybuddy.dtos.requests.*;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.LoginResponseDto;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.SocialLoginResponse;
import com.decagon.dev.paybuddy.enums.ResponseCodeEnum;
import com.decagon.dev.paybuddy.enums.Roles;
import com.decagon.dev.paybuddy.enums.WalletStatus;
import com.decagon.dev.paybuddy.exceptions.EmailNotConfirmedException;
import com.decagon.dev.paybuddy.models.ResetPasswordToken;
import com.decagon.dev.paybuddy.models.Role;
import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.models.Wallet;
import com.decagon.dev.paybuddy.repositories.RoleRepository;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import com.decagon.dev.paybuddy.repositories.WalletRepository;
import com.decagon.dev.paybuddy.restartifacts.BaseResponse;
import com.decagon.dev.paybuddy.security.CustomUserDetailService;
import com.decagon.dev.paybuddy.security.JwtUtils;
import com.decagon.dev.paybuddy.services.EmailService;
import com.decagon.dev.paybuddy.services.UserService;
import com.decagon.dev.paybuddy.utilities.AppUtil;
import com.decagon.dev.paybuddy.utilities.ResponseCodeUtil;
import com.decagon.dev.paybuddy.utilities.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Value("${forgot.password.url:http://localhost:3000/reset-password/}")
    private String forgotPasswordUrl;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtil;
    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService customUserDetailService;
    private final AppUtil appUtil;
    private final UserUtil userUtil;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    private final WalletRepository walletRepository;
    private final RoleRepository roleRepository;

    @Override
    public BaseResponse signUp(CreateUserRequest createUserRequest){
        BaseResponse response = new BaseResponse();

        if (createUserRequest.getFirstName().trim().length() == 0 ||
                createUserRequest.getLastName().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "First name cannot be empty.");

        if (!appUtil.validEmail(createUserRequest.getEmail()))
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "Invalid email address.");

        if (createUserRequest.getPhoneNumber().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "Last name cannot be empty.");

//        if (createUserRequest.getBvn().trim().length() == 0)
//            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
//                    "Bvn cannot be empty.");

        if (createUserRequest.getPassword().trim().length() == 0)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "Password cannot be empty.");

        Boolean isUserExist = userRepository.existsByEmail(createUserRequest.getEmail());

        if (isUserExist)
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "User already exist.");

        User newUser = new User();
        newUser.setFirstName(createUserRequest.getFirstName());
        newUser.setLastName(createUserRequest.getLastName());
        newUser.setOtherName(createUserRequest.getOtherName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setPhoneNumber(appUtil.getFormattedNumber(createUserRequest.getPhoneNumber()));
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        String token = jwtUtil.generateSignUpConfirmationToken(createUserRequest.getEmail());
        newUser.setConfirmationToken(token);
        userRepository.save(newUser);

        String URL = "http://localhost:8080/api/v1/auth/confirmRegistration?token=" + token;
        String link = "<h3>Hello "  + createUserRequest.getFirstName()  +
                "<br> Click the link below to activate your account <a href=" + URL + "><br>Activate</a></h3>";

        String subject = "Pay-Buddy Verification Code";

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(createUserRequest.getEmail());
        emailSenderDto.setSubject(subject);
        emailSenderDto.setContent(link);
        emailService.sendMail(emailSenderDto);

        return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS,
                "You have successful registered. Check your email to verify your account");
    }
    @Override
    public BaseResponse confirmRegistration(String token) {
        BaseResponse response = new BaseResponse();
        Optional<User> existingUser = userRepository.findByConfirmationToken(token);
        if (existingUser.isPresent()) {

            User user = existingUser.get();
            user.setRoles(getUserRoles(Collections.singleton(String.valueOf(Roles.ROLE_USER))));
            user.setConfirmationToken(null);
            user.setIsEmailVerified(true);
            userRepository.save(user);

            createWallet(user);

            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.SUCCESS,
                    "Account verification successful");
        } else {
            return responseCodeUtil.updateResponseData(response, ResponseCodeEnum.ERROR,
                    "User not found");
        }
    }

    private Collection<Role> getUserRoles(Collection<String> roleNames) {
        Collection<Role> roles = new HashSet<>();
        if (roleNames == null || roleNames.isEmpty()) {
            roles.add(roleRepository.findByName(Roles.ROLE_USER.name()));
            return roles;
        }
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        if (roles.size() == 0)
            roles.add(roleRepository.findByName(Roles.ROLE_USER.name()));


        if (roles.stream().anyMatch(role -> role.getName().equals(Roles.ROLE_SUPER_ADMIN.name())))
            throw new RuntimeException("ErrorMessages.ACCESS_DENIED.getErrorMessage()");
        return roles;
    }
    @Override
    public BaseResponse login(LoginUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()
                -> new BadCredentialsException("Bad credentials"));

        if (!user.getIsEmailVerified())
            throw new EmailNotConfirmedException("Kindly confirm your email address");

        Authentication authentication;
        try {
             authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("Bad credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        LoginResponseDto responseDto = LoginResponseDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .loginCount(user.getLoginCount())
                .token(token)
                .build();
        responseDto.setCode(0);
        responseDto.setDescription(ResponseCodeEnum.SUCCESS.getDescription());

        if (user.getLoginCount() == 0) {
            user.setLoginCount(1);
            userRepository.save(user);
        }

        return responseCodeUtil.updateResponseDataReturnObject(new BaseResponse(), ResponseCodeEnum.SUCCESS, responseDto);
    }

    @Override
    public BaseResponse forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest) {

        BaseResponse baseResponse = new BaseResponse();
        String email = forgotPasswordRequest.getEmail();
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            String generatedToken = jwtUtil.generatePasswordResetToken(email);

            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            resetPasswordToken.setToken(generatedToken);
            resetPasswordToken.setUser(user.get());

            String link = String.format("%s%s", forgotPasswordUrl, generatedToken + " expires in 15 minutes.");
            EmailSenderDto emailSenderDto = new EmailSenderDto();
            emailSenderDto.setTo(forgotPasswordRequest.getEmail());
            emailSenderDto.setSubject("Forgot Password Token");
            emailSenderDto.setContent(link);
            emailService.sendMail(emailSenderDto);
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS,
                    "Check your email for password reset instructions");
        } else {
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR,
                    "User not found");
        }
    }

    @Override
    public BaseResponse resetPassword(ResetPasswordRequest request, String token) {
        String email = jwtUtil.extractUsername(token);
        BaseResponse baseResponse = new BaseResponse();
        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "Passwords do not match");

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user.get());
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Password Reset Successful");
        } else {
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "User not found");
        }
    }

    @Override
    public SocialLoginResponse socialLogin(SocialLoginUserRequest request) {
     Optional<User> userFound = userRepository.findByEmail(request.getEmail());

        if (userFound.isEmpty()) {
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            String email = request.getEmail();

            User createUser = new User();
            createUser.setFirstName(firstName);
            createUser.setLastName(lastName);
            createUser.setEmail(email);
            createUser.setIsEmailVerified(true);
            createUser.setRoles(getUserRoles(Collections.singleton(String.valueOf(Roles.ROLE_USER))));
            createUser.setLoginCount(1);
            userRepository.save(createUser);
            createWallet(createUser);
        }

        String token = jwtUtil.generateToken(request.getEmail());
        return new SocialLoginResponse(token, userFound.map(User::getLoginCount).orElse(0));
    }

    private void createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setAccountNumber(appUtil.generateAccountNumber(user.getUserId(), user.getEmail()));
        wallet.setAccountBalance(BigDecimal.ZERO);
        wallet.setPin(passwordEncoder.encode("0000"));
        wallet.setUser(user);
        wallet.setStatus(WalletStatus.LOCKED);
        walletRepository.save(wallet);
    }

}
