package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;
import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.exceptions.EmailAlreadyTakenException;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import com.decagon.fintechpaymentapisqd11a.request.FlwWalletRequest;
import com.decagon.fintechpaymentapisqd11a.response.UserResponse;
import com.decagon.fintechpaymentapisqd11a.services.UsersService;
import com.decagon.fintechpaymentapisqd11a.services.WalletService;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;
import com.decagon.fintechpaymentapisqd11a.util.Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
@Builder
public class UserServiceImpl implements UserDetailsService, UsersService {
    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final WalletService walletService;
    private final ConfirmationTokenServiceImpl tokenService;
    private final WalletRepository walletRepository;
    private final Util util;

    @Override
    public String registerUser(RegistrationRequestDto registrationRequestDto) throws JSONException {

        Users user = new Users();
        boolean userExists = usersRepository.findByEmail(registrationRequestDto.getEmail()).isPresent();
        boolean passwordMatch = util.validatePassword(registrationRequestDto.getPassword(),
                registrationRequestDto.getConfirmPassword());

        if (userExists) {
            throw new EmailAlreadyTakenException("Email Already Taken");
        }
        if (!passwordMatch) {
            throw new InputMismatchException("Passwords do not match!");
        }

        registrationRequestDto.setTransactionPin(passwordEncoder
                .encode(registrationRequestDto.getTransactionPin()));
        registrationRequestDto.setPassword(passwordEncoder.
                encode(registrationRequestDto.getPassword()));

        BeanUtils.copyProperties(registrationRequestDto, user);
        Users user1 = usersRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        confirmationTokenRepository.save(confirmationToken);

        Wallet wallet = walletService.createWallet(FlwWalletRequest.builder()
                .firstname(user1.getFirstName())
                .lastname(user1.getLastName())
                .email(user1.getEmail())
                .phonenumber(user1.getPhoneNumber())
                .bvn(user1.getBvn())
                .build());
        wallet.setUsers(user1);
        wallet.setBalance(0.00);
        walletRepository.save(wallet);
        return token;
    }

    @Override
    public void enableUser(String email) {
        Users users = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        users.setUserStatus(UserStatus.ACTIVE);
        usersRepository.save(users);
    }

    @Override
    public void saveToken(String token, Users users) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                users
        );
        tokenService.saveConfirmationToken(confirmationToken);

    }

    @Override
    public UserResponse getUser() {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = usersRepository.findByEmail(user1.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .bvn(user.getBvn())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        Optional<Users> users = usersRepository.findByEmail(email);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        if (users.isEmpty()) {
            throw new UserNotFoundException("Email not found in database");
        } else {
            return new User(users.get().getEmail(), users.get().getPassword(), Collections.singleton(authority));
        }

    }

}







