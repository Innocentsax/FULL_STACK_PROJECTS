package com.decagon.adire.service.implementation;

import com.decagon.adire.dto.request.DesignerDTO;
import com.decagon.adire.dto.response.*;
import com.decagon.adire.dto.request.*;
import com.decagon.adire.dto.response.UserResponseDto;
import com.decagon.adire.entity.Designer;
import com.decagon.adire.enums.Provider;
import com.decagon.adire.exception.BadRequestException;
import com.decagon.adire.exception.NotFoundException;
import com.decagon.adire.exception.UnAuthorizedException;
import com.decagon.adire.mail.EmailService;
import com.decagon.adire.repository.DesignerRepository;

import com.decagon.adire.security.jwt.TokenProvider;
import com.decagon.adire.service.DesignerService;
import com.decagon.adire.utils.AppConstants;
import com.decagon.adire.utils.AppUtil;
import com.decagon.adire.utils.EmailValidatorService;
import com.decagon.adire.utils.SecurityConstants;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class DesignerServiceImpl implements DesignerService{

    private final AuthenticationManager authenticationManager;

    private final DesignerRepository designerRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;
    private ModelMapper modelMapper = new ModelMapper();
    @Value("${spring.googleDefaultPassword}")
    private String googleDefaultPassword;

    @Override
    public LoginResponse login(LoginDTO request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }catch (BadCredentialsException e) {
            throw new UnAuthorizedException("invalid email or password");
        }
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String response = String.valueOf(TokenProvider.generate(authentication));

            log.info("controller login: login user :: [{}] ::", request.getEmail());
            Designer user = designerRepository.findByEmail(authentication.getName()).<NotFoundException>orElseThrow(
                    () -> {
                        throw new NotFoundException("user does not exist");
                    }
            );
            return mapToLoginResponse(user, response);
        } else {
            throw new NotFoundException("invalid user request !");
        }
    }

    @Override
    public DesignerResponseDTO updateDesignerDetials(UpdateDesigner updateUserDto) {
        log.info("service updateUser - updating user with email :: [{}] ::", updateUserDto.getEmail());
        Designer user = designerRepository.findByEmail(updateUserDto.getEmail()).<NotFoundException>orElseThrow(
                () -> {
                    throw new NotFoundException("user does not exist");
                }
        );
        if (StringUtils.isNoneBlank(updateUserDto.getFirstName()))
            user.setFirstName(updateUserDto.getFirstName());
        if (StringUtils.isNoneBlank(updateUserDto.getLastName()))
            user.setLastName(updateUserDto.getLastName());
        if (StringUtils.isNoneBlank(updateUserDto.getEmail()))
            user.setEmail(updateUserDto.getEmail());
        if (StringUtils.isNoneBlank(updateUserDto.getPhoneNumber()))
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        if (StringUtils.isNoneBlank(updateUserDto.getUrl()))
            user.setUrl(updateUserDto.getUrl());
        designerRepository.save(user);
        return mapToResponseDto(user);
    }


    @Override
    public DesignerResponseDTO createUser(DesignerDTO request) {
        if ("".equals(request.getEmail().trim())) {
            throw new NotFoundException("Email can not be empty");
        }
        String email = request.getEmail().toLowerCase();
        if(!EmailValidatorService.isValid(email)){
            throw new BadRequestException("Enter a valid email address");
        }
        var newUser = designerRepository.findByEmail(email);
        if (newUser.isPresent()) {
            throw new BadRequestException("User already exist");
        }
        Designer user = new Designer();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUrl(request.getUrl());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(AppConstants.USER);
        user.setProvider(Provider.LOCAL);
        log.info("about saving");
        designerRepository.save(user);
        emailService.sendAccountCreationNotification(user.getFirstName(), user.getLastName(), user.getEmail());
        return mapToResponseDto(user);
    }

    @Override
    public UserResponseDto saveOAuth2User(Designer request) {
            Designer saveUser = designerRepository.save(request);
            log.info("saved oauth2 user");
            return new UserResponseDto(saveUser.getEmail(), saveUser.getRole());
    }

    @Override
    public DesignerResponseDTO getDesigner(String Id) {
        Designer optionalDesigner = designerRepository.findById(Id).orElseThrow(() -> {
                    throw new NotFoundException("Designer not found");
                }
        );
        return mapToResponseDto(optionalDesigner);
    }




    @Override
    public String updatePassword(UpdatePasswordDto updatePasswordDto) {
        String email = AppUtil.extractEmailFromPrincipal()
                .orElseThrow(() -> new UnAuthorizedException("User is not authorized", HttpStatus.UNAUTHORIZED));

        String newPassword = updatePasswordDto.getNewPassword();
        String confirmNewPassword = updatePasswordDto.getConfirmNewPassword();
        String oldPassword = updatePasswordDto.getOldPassword();

        Designer user = designerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("This user does not exist"));
        System.out.println(user.getPassword());
        System.out.println(passwordEncoder.matches(oldPassword, user.getPassword()));
        System.out.println(passwordEncoder.encode(oldPassword));

        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "The existing password is invalid");
        }

        if(!confirmNewPassword.equals(newPassword)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Password does not match confirm password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        designerRepository.save(user);
        log.info("password reset successful");
        return "Password updated successfully";

    }


    private DesignerResponseDTO mapToResponseDto(Designer designer) {
        return modelMapper.map(designer, DesignerResponseDTO.class);
    }
    private LoginResponse mapToLoginResponse(Designer designer, String token) {
        return LoginResponse.builder()
                .email(designer.getEmail())
                .firstName(designer.getFirstName())
                .lastName(designer.getLastName())
                .url(designer.getUrl())
                .phoneNumber(designer.getPhoneNumber())
                .token(token)
                .build();
    }

    @Override
    public Designer findByEmail(String email) {
        return designerRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new NotFoundException("user not found");
                }
        );
    }

    @Override
    public void deleteDesigner(String email) {
        var designer = designerRepository.findByEmail(email);
        if(designer.isEmpty()){
            throw new NotFoundException("Designer not found");
        }
        designerRepository.deleteById(designer.get().getId());
    }
    @Override
    public LoginResponse loginWithGoogle(Oauth2Request request) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(SecurityConstants.clientId))
                .build();
        GoogleIdToken idTokenObj = verifier.verify(request.getAccessToken());
        Designer user = null;
        if (idTokenObj != null) {
            log.info("{}",idTokenObj);
            GoogleIdToken.Payload payload = idTokenObj.getPayload();

            String email = payload.getEmail();
            String firstname = (String) payload.get("given_name");
            String lastname = (String) payload.get("family_name");
            String image = (String) payload.get("picture");

            user = designerRepository.findByEmail(email).orElse(null);
            if (user == null) {
                user = new Designer();
                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setEmail(email);
                user.setRole(AppConstants.USER);
                user.setUrl(image);
                user.setProvider(Provider.GOOGLE);
                user.setPassword(passwordEncoder.encode(googleDefaultPassword));

                user = designerRepository.save(user);

            }
            log.info("authenticating user");
            log.info("firstname ->{}  lastname->{} email ->{}", user.getFirstName(), user.getLastName(), user.getEmail());
        } else {
            log.error("idTokenObj is null");
            throw new BadRequestException("Bad Credentials !");
        }

        return mapToGoogleLoginResponse(user, generateGoogleToken(user.getEmail(), googleDefaultPassword));
    }

    private LoginResponse mapToGoogleLoginResponse(Designer user, String token) {
        return LoginResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .url(user.getUrl())
                .token(token)
                .build();
    }



    private String generateGoogleToken(String email, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (BadCredentialsException e) {
            throw new UnAuthorizedException("invalid email or password");
        }if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return String.valueOf(TokenProvider.generate(authentication));
        } else {
            throw new NotFoundException("invalid user request !");
        }
    }
    @Override
    public DesignerResponseDTO getDesignerProfile(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring(SecurityConstants.TOKEN_PREFIX.length());
        String userName = TokenProvider.getUsernameFromToken(token);
        log.info("User name: {}", userName);
        Designer user = designerRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        DesignerResponseDTO userResponse = modelMapper.map(user, DesignerResponseDTO.class);
        return userResponse;
    }



    }



