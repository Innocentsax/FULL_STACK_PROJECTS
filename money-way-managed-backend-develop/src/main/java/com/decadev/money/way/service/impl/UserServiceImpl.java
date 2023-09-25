package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.request.ChangePasswordRequest;
import com.decadev.money.way.dto.request.ChangeTransactionPinRequest;
import com.decadev.money.way.dto.request.RegisterRequest;
import com.decadev.money.way.dto.request.VirtualAccountCreationRequest;
import com.decadev.money.way.dto.response.VirtualAccountCreationResponse;
import com.decadev.money.way.enums.Role;
import com.decadev.money.way.exception.*;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.VerificationToken;
import com.decadev.money.way.model.Wallet;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.repository.VerificationTokenRepository;
import com.decadev.money.way.service.UserService;
import com.decadev.money.way.service.WalletService;
import com.decadev.money.way.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final SecurityUtils securityUtils;
    private final WalletService walletService;

    @Override
    public User registerUser(RegisterRequest request) throws UserAlreadyExistsException, IOException {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .role(Role.USER)
                .pin(passwordEncoder.encode(request.getPin()))
                .build();

        User savedUser =  userRepository.save(newUser);
        //Create new user virtual account
       VirtualAccountCreationResponse virtualAccountCreationResponse= walletService.createVirtualAcc(
                VirtualAccountCreationRequest.builder()
                        .email(request.getEmail())
                        .bvn(request.getBvn())
                        .firstname(request.getFirstName())
                        .lastname(request.getLastName())
                        .is_permanent(true)
                        .narration(request.getFirstName()+" "+request.getLastName())
                        .phonenumber(request.getPhoneNumber())
                        .build()
        );

        //Create new user's wallet
        Wallet userWallet = walletService.createWallet(virtualAccountCreationResponse, savedUser);

        savedUser.setWallet(userWallet);

        return userRepository.save(savedUser);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String changeTransactionPin(ChangeTransactionPinRequest request) throws UserNotFoundException {
        User user = userRepository.findByEmail(securityUtils.getCurrentUserDetails().getUsername()).orElseThrow(()->new UserNotFoundException("User Not found"));

        if(!passwordEncoder.matches(request.getOldPin(), user.getPin()))throw new TransactionException("Wrong Pin input");

        if(!request.getConfirmNewPin().equals(request.getNewPin()))throw new TransactionException("New pin entry conflict");

        user.setPin(passwordEncoder.encode(request.getNewPin()));
        userRepository.save(user);
        return "Pin Changed Successfully";
    }

    @Override
    public boolean verifyCurrentPassword(User user, String enteredPassword) {
        return passwordEncoder.matches(enteredPassword, user.getPassword());
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
        String email = loggedInUser.getUsername();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User Not Found!"));

        if (!verifyCurrentPassword(user, changePasswordRequest.getCurrentPassword())) {
            throw new PasswordMismatchException("Password Does Not Match, Enter a correct Password");
        } else {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
        }

        return "Password Changed Successfully";
    }


}
