package com.example.decapay.services.impl;

import com.example.decapay.configurations.mails.EmailSenderService;
import com.example.decapay.configurations.security.CustomUserDetailService;
import com.example.decapay.configurations.security.JwtUtils;
import com.example.decapay.enums.Status;
import com.example.decapay.enums.VerificationType;
import com.example.decapay.exceptions.NotFoundException;
import com.example.decapay.exceptions.UserAlreadyExistException;
import com.example.decapay.exceptions.UserNotFoundException;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.Token;
import com.example.decapay.models.User;
import com.example.decapay.pojos.requestDtos.*;
import com.example.decapay.pojos.responseDtos.LoginResponseDto;
import com.example.decapay.pojos.responseDtos.TokenVerificationResponse;
import com.example.decapay.pojos.responseDtos.UpdateProfileResponseDto;
import com.example.decapay.pojos.responseDtos.UserResponseDto;
import com.example.decapay.repositories.TokenRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.UserService;
import com.example.decapay.utils.CloudinaryUtils;
import com.example.decapay.utils.MailSenderUtil;
import com.example.decapay.utils.UserIdUtil;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Value("${forgot.password.url:http://localhost:5432/Api/v1/auth/verify-token/}")
    private String forgotPasswordUrl;

    private final UserRepository userRepository;
    private final UserUtil userUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final TokenRepository tokenRepository;

    private final MailSenderUtil mailSenderUtil;

    private final UserIdUtil idUtil;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    private final CloudinaryUtils cloudinaryUtils;


    @Override
    public UserResponseDto createUser(UserRequestDto request) throws  MessagingException {

        if(userRepository.findByEmail(request.getEmail()).isPresent())
            throw new UserAlreadyExistException("User already exist");

        boolean matches = request.getPassword().equals(request.getConfirmPassword());

        if(!matches) throw new ValidationException("password and confirm password do not match.");

        User newUser = new User();

        BeanUtils.copyProperties(request,newUser);

        String publicUserId = idUtil.generatedUserId(20);
        newUser.setUserId(publicUserId);

        newUser.setPassword(passwordEncoder.encode(request.getPassword()));








        User savedUser = userRepository.save(newUser);
        savedUser.setImagePath(cloudinaryUtils.defaultImageUpload(savedUser));

        mailSenderUtil.verifyMail(savedUser);

        UserResponseDto responseDto = new UserResponseDto();

        BeanUtils.copyProperties(savedUser,responseDto);



        return responseDto;

    }

    @Override
    public ResponseEntity<LoginResponseDto> userLogin(LoginRequestDto loginRequestDto) {
         authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        final UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequestDto.getEmail());
        if (userDetails != null) {
            Optional<User> userOptional = userRepository.findByEmail(loginRequestDto.getEmail());
            User user = userOptional.get();
            LoginResponseDto loginResponse = LoginResponseDto.builder()
                    .userId(user.getUserId())
                    .lastName(user.getLastName())
                    .firstName(user.getFirstName())
                    .email(user.getEmail())
                    .imagePath(user.getImagePath())
                    .phoneNumber(user.getPhoneNumber())
                    .token(jwtUtils.generateToken(userDetails))
                    .build();

            return ResponseEntity.ok(loginResponse);
        }

        return ResponseEntity.status(400).body(LoginResponseDto.builder().build());
    }



    @Override
    @Transactional
    public ResponseEntity<LoginResponseDto> editUser(UserUpdateRequest userUpdateRequest) {

        String email = userUtil.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());

        User updatedUser = userRepository.save(user);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .phoneNumber(updatedUser.getPhoneNumber())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .build();

        return ResponseEntity.ok(loginResponseDto);
    }


    @Override
    public String forgotPasswordRequest(ForgetPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        String generatedToken = jwtUtils.generatePasswordResetToken(email);

        Token token = new Token();
        token.setToken(generatedToken);
        token.setStatus(Status.ACTIVE);
        token.setVerificationType(VerificationType.RESET_PASSWORD);
        token.setUser(user);

        tokenRepository.save(token);

        String link = String.format("%s%s", forgotPasswordUrl, generatedToken + " expires in 15 minutes.");
        emailSenderService.sendPasswordResetEmail( forgotPasswordRequest.getEmail(), "forgot Password token", link);
        return "Check your email for password reset instructions";
    }

    @Override
    public String resetPassword(ResetPasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmPassword()))
            throw new InputMismatchException("Passwords do not match");



        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

//        Token tokenEntity = tokenRepository.findByToken(token)
//                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Token does not exist."));
//
//        if (tokenEntity.getStatus().equals(Status.EXPIRED))
//            throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST, "Token expired.");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        //todo: to be removed

//        tokenEntity.setStatus(Status.EXPIRED);
//        tokenRepository.save(tokenEntity);

        return "Password reset successful";
    }

    @Override
    public TokenVerificationResponse verifyToken(String token) {
        TokenVerificationResponse tokenResponse = new TokenVerificationResponse();
        Token passwordResetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("token does not exist"));
        //todo: update user verification status
        //todo : user verification status updated
        passwordResetToken.setStatus(Status.VERIFIED);
        tokenRepository.save(passwordResetToken);

        tokenResponse.setToken(passwordResetToken.getToken());
        tokenResponse.setStatus(passwordResetToken.getStatus());
        tokenResponse.setEmail(passwordResetToken.getUser().getEmail());

        System.out.println(tokenResponse);
        return tokenResponse;
    }

    @Override
    public ResponseEntity<UpdateProfileResponseDto> uploadProfilePicture(MultipartFile image) throws IOException, UserNotFoundException {
        String email = userUtil.getAuthenticatedUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found",HttpStatus.NOT_FOUND,"Contact Admin"));
        String pictureUrl = cloudinaryUtils.createOrUpdateImage(image, user);
        if (pictureUrl.equals("unsuccessful"))
            return ResponseEntity.unprocessableEntity().body(UpdateProfileResponseDto.builder().error("Network not available at the moment. please try again later").build());
        user.setImagePath(pictureUrl);
        userRepository.save(user);
        UpdateProfileResponseDto returnValue = UpdateProfileResponseDto.builder()
                .imageUrl(pictureUrl)
                .build();
        return ResponseEntity.ok(returnValue);
    }

    @Override
    public void verifyUserExists(String userEmail) {
        userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(
                        HttpStatus.BAD_REQUEST, "User with email: " + userEmail + " Not Found"));
     }

    @Override
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User with email: " + email + " Not Found",HttpStatus.BAD_REQUEST,"Contact Admin"));
    }
}
