package com.decagon.chompapp.services.Impl;

import com.decagon.chompapp.dtos.SignUpDto;
import com.decagon.chompapp.models.Cart;
import com.decagon.chompapp.models.Role;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.repositories.CartRepository;
import com.decagon.chompapp.repositories.RoleRepository;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.security.JwtTokenProvider;
import com.decagon.chompapp.services.EmailSenderService;
import com.decagon.chompapp.services.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
//@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSender;
    private final JwtTokenProvider jwtTokenProvider;
    private final CartRepository cartRepository;


    @Override
    public ResponseEntity<String> registerUser(SignUpDto signUpDto, HttpServletRequest request) throws MalformedURLException {
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setFirstName(signUpDto.getFirstName());
        user.setUsername(signUpDto.getUsername());
        user.setLastName(signUpDto.getLastName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Optional<Role> roles = roleRepository.findByName("ROLE_PREMIUM");
        user.setRoles(Collections.singleton(roles.get()));
        user.setIsEnabled(false);


        return verifyRegistration(user, request);
    }

    @Override
    public ResponseEntity<String> verifyRegistration(User user, HttpServletRequest request) throws MalformedURLException {
        String token = jwtTokenProvider.generateSignUpConfirmationToken(user.getEmail());
        user.setConfirmationToken(token);
        User savedUser = userRepository.save(user);
        Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        emailSender.sendRegistrationEmail(user.getEmail(), token);
        return new ResponseEntity<>("User registered successfully. Kindly check your mail inbox or junk folder to verify your account", HttpStatus.OK );
    }
    @Override
    public ResponseEntity<String> verifyRegistration(long id) throws MalformedURLException {
        Optional<User> userCheck = userRepository.findById(id);
        if (userCheck.isPresent()) {
            User user = userCheck.get();
            String token = jwtTokenProvider.generateSignUpConfirmationToken(user.getEmail());
            user.setConfirmationToken(token);
            userRepository.save(user);
            emailSender.sendRegistrationEmail(user.getEmail(), token);
        } else {
            throw new RuntimeException("User with this email not found");
        }
            return new ResponseEntity<>("Kindly check your mail inbox or junk folder to verify your account", HttpStatus.OK );
    }

    @Override
    public ResponseEntity<String> confirmRegistration(String token) {
        if (jwtTokenProvider.validateToken(token)){
            Optional<User> existingUser = userRepository.findByConfirmationToken(token);
            if (existingUser.isPresent()) {
                existingUser.get().setConfirmationToken(null);
                existingUser.get().setIsEnabled(true);
                userRepository.save(existingUser.get());
                return new ResponseEntity<>("Account verification successful", HttpStatus.ACCEPTED);
            } else {
                throw new RuntimeException("User not found!");
            }
        }
        throw new RuntimeException("JWT token expired");
    }
}
