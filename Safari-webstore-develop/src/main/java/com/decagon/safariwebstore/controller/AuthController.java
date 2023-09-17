package com.decagon.safariwebstore.controller;


import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.ERole;
import com.decagon.safariwebstore.model.Role;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.request.UpdatePasswordRequest;
import com.decagon.safariwebstore.payload.request.auth.LoginRequest;
import com.decagon.safariwebstore.payload.request.auth.RegisterUser;
import com.decagon.safariwebstore.payload.response.UserDTO;
import com.decagon.safariwebstore.payload.response.auth.LoginResponse;
import com.decagon.safariwebstore.repository.RoleRepository;
import com.decagon.safariwebstore.security.service.UserDetailService;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.JWTUtil;
import com.decagon.safariwebstore.utils.mailService.MailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api")
public class
AuthController {


    private final UserService userService;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final JWTUtil jwtUtil;

    @Autowired
    private final MessageSource messages;

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody RegisterUser registerUser) throws UnirestException {
        User user = userService.registration(registerUser);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        User savedUser = userService.saveUser(user);
        if(savedUser != null){
            mailService.sendMessage(registerUser.getEmail(), "Test mail", "Registration successful");
        }

        return UserDTO.build(user);
    }

    @PostMapping("/admin/register")
    public UserDTO registerAdmin(@Valid @RequestBody RegisterUser registerUser) throws UnirestException {
        User user = userService.registration(registerUser);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        // send mail...
        User savedUser = userService.saveUser(user);
        if(savedUser != null){
            mailService.sendMessage(registerUser.getEmail(), "Test mail", "Registration successful");
        }

        return UserDTO.build(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@Valid @RequestBody LoginRequest request){


        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()
                        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        final String jwtToken = jwtUtil.generateToken(userDetails);

        LoginResponse response = new LoginResponse(jwtToken, roles);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Change/Reset Password

    @PostMapping("/updatePassword")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @Secured({"ADMIN","USER"})
    public ResponseEntity<String> changeUserPassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {

        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.checkIfValidOldPassword(user, updatePasswordRequest);
        boolean passwordIsChanged = userService.changeUserPassword(user, updatePasswordRequest);
        if (passwordIsChanged) {
            return ResponseEntity.ok("Password Changed Successfully");
        }
        return new ResponseEntity<>("Error changing password", HttpStatus.BAD_REQUEST);
    }

}
