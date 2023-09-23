package com.example.hive.service.implementation;


import com.example.hive.dto.request.UserRegistrationRequestDto;
import com.example.hive.dto.response.UserRegistrationResponseDto;
import com.example.hive.entity.*;
import com.example.hive.exceptions.ResourceNotFoundException;
import com.example.hive.repository.*;
import com.example.hive.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.hive.entity.User;
import com.example.hive.enums.Role;
import com.example.hive.exceptions.CustomException;
import com.example.hive.repository.UserRepository;
import com.example.hive.utils.event.RegistrationCompleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).get().getUser());

    }

    @Override
    @Transactional
    public UserRegistrationResponseDto registerUser(UserRegistrationRequestDto registrationRequestDto, HttpServletRequest request) {
        log.info("register user and create account");

        if (doesUserAlreadyExist(registrationRequestDto.getEmail())) {
            throw new CustomException("User already exist", HttpStatus.FORBIDDEN);
        }
        User newUser = saveNewUser(registrationRequestDto);
        //reate a wallet account for both doer and tasker

        createWalletAccount(newUser);

        // generateToken and Save to token repo, send email also
        eventPublisher.publishEvent(new RegistrationCompleteEvent(
                newUser,getVerificationUrl(request)
        ));
        return modelMapper.map(newUser, UserRegistrationResponseDto.class);
    }

    private void createWalletAccount(User newUser) {
        Wallet newWallet = new Wallet();
        newWallet.setUser(newUser);
        walletRepository.save(newWallet);
    }

    private boolean doesUserAlreadyExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Boolean validateRegistrationToken(String token) {
        Boolean status = false;
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(token)
                        .orElseThrow(() -> new CustomException("Token does not Exist : "+ token, HttpStatus.BAD_REQUEST));
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        // check if user is already verified
        if (user.getIsVerified()) {
            verificationTokenRepository.delete(verificationToken);
            throw new CustomException("User is already verified", HttpStatus.BAD_REQUEST);
        }
        // check if token is expired
        if((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0){
            throw new CustomException("Token has expired", HttpStatus.BAD_REQUEST);

        }
        // check if token is valid
        if (verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() > 0 ) {
            user.setIsVerified(true);
            userRepository.save(user);
            // activate the wallet
            activateWallet(user);
            verificationTokenRepository.delete(verificationToken);
            status = true;
        }
        return status;
    }

    @Override
    public  String generateVerificationToken(User user) {
        log.info("inside generateVerificationToken, generating token for {}", user.getEmail());
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 900000);
        verificationToken.setExpirationTime(expirationDate);

        log.info("Saving token to database {}", token);
        verificationTokenRepository.save(verificationToken);
        return token;
    }
    @Override
    public VerificationToken generateNewToken(User user) {
        // does the user have a saved (old)token?
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user)
                .orElseThrow(() -> new CustomException("Token does not Exist", HttpStatus.BAD_REQUEST));
        verificationToken.setToken(UUID.randomUUID().toString());
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + 900000);
        verificationToken.setExpirationTime(expirationDate);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
    //HELPER METHODS

    private User saveNewUser(UserRegistrationRequestDto registrationRequestDto) {
        User newUser = new User();
        Role role = registrationRequestDto.getRole();
        BeanUtils.copyProperties(registrationRequestDto, newUser);
        log.info("user has a role of {}",registrationRequestDto.getRole().toString());
        newUser.addRole(role);
        log.info("user now has a role of {}",newUser.getRoles().toString());
        newUser.setPassword(passwordEncoder.encode(registrationRequestDto.getPassword()));


        return userRepository.save(newUser);
    }
    private void activateWallet(User user) {
        Wallet wallet = walletRepository.findByUser(user).orElseThrow( () -> new CustomException("User does not have a wallet"));
        wallet.setActivated(true);
        walletRepository.save(wallet);
    }

    private static String getVerificationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/auth";
    }
}
