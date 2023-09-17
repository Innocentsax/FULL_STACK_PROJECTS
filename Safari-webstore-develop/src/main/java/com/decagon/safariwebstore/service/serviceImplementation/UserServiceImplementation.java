package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.dto.UserDetailsDTO;
import com.decagon.safariwebstore.exceptions.BadRequestException;
import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.ERole;
import com.decagon.safariwebstore.model.Role;
import com.decagon.safariwebstore.model.User;
import com.decagon.safariwebstore.payload.request.auth.EditUser;
import com.decagon.safariwebstore.payload.request.UpdatePasswordRequest;
import com.decagon.safariwebstore.payload.request.auth.RegisterUser;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.payload.response.auth.ResetPassword;
import com.decagon.safariwebstore.repository.RoleRepository;
import com.decagon.safariwebstore.payload.response.UserDTO;
import com.decagon.safariwebstore.repository.UserRepository;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.DateUtils;
import com.decagon.safariwebstore.utils.mailService.MailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByMail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User registration(RegisterUser registerUser){
        if(userRepository.existsByEmail(registerUser.getEmail())) {
            throw new BadRequestException("Error: Email is already taken!");
        }
        if(!(registerUser.getPassword().equals(registerUser.getConfirmPassword()))){
            throw new BadRequestException("Error: Password does not match");
        }
        if(!isValidPassword(registerUser.getPassword())){
            throw new BadRequestException("Error: Password must be between 8 and 20, must be an Alphabet or Number");
        }
        if(!isValidEmail(registerUser.getEmail())){
            throw new BadRequestException("Error: Email must be valid");
        }
        return new User(
                registerUser.getFirstName(),
                registerUser.getLastName(),
                registerUser.getEmail(),
                registerUser.getGender(),
                registerUser.getDateOfBirth(),
                bCryptPasswordEncoder.encode(registerUser.getPassword())
        );
    }

    private boolean isValidPassword(String password) {
        String regex = "^(([0-9]|[a-z]|[A-Z]|[@])*){8,20}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            throw new BadRequestException("Error: Password cannot be null");
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private boolean isValidEmail(String email) {
        String regex = "^(.+)@(\\w+)\\.(\\w+)$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (email == null) {
            throw new BadRequestException("Error: Email cannot be null");
        }
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findByPasswordResetToken(resetToken);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * This method is called by the scheduler every 60 minutes
     * to check if the time to invalidate the token has reached limit
     * */
    @Override
    public void deactivateResetPasswordToken() {
        List<User> accountsList = userRepository.findAllByPasswordResetTokenIsNotNull();
        accountsList.forEach(account -> {
            String expireDate = account.getPasswordResetExpireDate();
            String presentDate = DateUtils.getCurrentTime();
            int actionDelete = presentDate.compareTo(expireDate);
            if(actionDelete > 0 || actionDelete == 0) {
                account.setPasswordResetExpireDate(null);
                account.setPasswordResetToken(null);
                userRepository.save(account);
            }
        });
    }

    /**
     * Sends an email to the customer with a url link to reset password
     * the url link will be received in the frontend
     * @param request
     * @param accountEmail
     * @return object
     * */


    @Override
    public ResponseEntity<Response> userForgotPassword(HttpServletRequest request, String accountEmail)
    {
        // Lookup user in database by e-mail
        Optional<User> optionalUser = userRepository.findByEmail(accountEmail);
        //response handler
        Response responseHandler = new Response();

        Role roleUser = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));

        if(!optionalUser.isPresent()) {
            responseHandler.setStatus(404);
            responseHandler.setMessage("We couldn't find an account with that e-mail address.");
            return new ResponseEntity<>(responseHandler, HttpStatus.NOT_FOUND);
        }

        Role userRole = optionalUser.get().getRoles().get(0);
        if(userRole != roleUser){
            responseHandler.setStatus(401);
            responseHandler.setMessage("You don't have access to this link");
            return new ResponseEntity<>(responseHandler, HttpStatus.UNAUTHORIZED);
        }
        //process email
        try {
            // Generate random 36-character string token for reset password
            User user = optionalUser.get();
            user.setPasswordResetToken(UUID.randomUUID().toString());
            //24hours expiry date for token
            String tokenExpiryDate = DateUtils.passwordResetExpiryTimeLimit();
            user.setPasswordResetExpireDate(tokenExpiryDate);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            String subject = "Customer Reset Password";
            String mailBody = "To reset your password, click the link below:\n"
                    + appUrl + "/reset?token="
                    + user.getPasswordResetToken();
            mailService.sendMessage(user.getEmail(), subject, mailBody);
            // Save token and expiring date to database
            userRepository.save(user);
            responseHandler.setStatus(200);
            responseHandler.setMessage("Successfully sent email");
        }
        catch (UnirestException e){
            System.out.println("Error sending email:\n\tError message:"+e.getMessage());
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.OK);
    }

    /**
     * This method check the validity of the token sent and also validates passwords(password and confirm password)
     * before saving it
     * @param passwordReset
     * @return response
     * */
    public ResponseEntity<Response> userResetPassword(ResetPassword passwordReset){

        //find the user by the token
        Optional<User> userOptional = userRepository.findByPasswordResetToken(passwordReset.getToken());

        String password = passwordReset.getPassword();
        String confirmPassword = passwordReset.getConfirmPassword();

        //response handler
        Response responseHandler = new Response();

        if (userOptional.isEmpty()){
            responseHandler.setStatus(400);
            responseHandler.setMessage("Oops!  This is an invalid password reset link.");
            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();

        if(!password.equals(confirmPassword)){
            responseHandler.setStatus(400);
            responseHandler.setMessage("Passwords does not match");
            return new ResponseEntity<>(responseHandler, HttpStatus.BAD_REQUEST);
        }

        //set the encrypted password
        user.setPassword(bCryptPasswordEncoder.encode(password));
        // Set the reset token to null so it cannot be used again
        user.setPasswordResetToken(null);
        //set the reset passwordRestExpireDate to null
        user.setPasswordResetExpireDate(null);
        try {
            // Save user
            userRepository.save(user);
            responseHandler.setStatus(201);
            responseHandler.setMessage("You have successfully reset your password. You can now login.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseHandler, HttpStatus.CREATED);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; email " + email + " does not exist");
        return user.get();
    }

    @Override
    public UserDTO updateUser(EditUser user) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedUser = findUserByEmail(email);

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        loggedUser.setGender(user.getGender());
        loggedUser.setDateOfBirth(user.getDateOfBirth());

        if (!userRepository.existsByEmail(user.getEmail())) {
            loggedUser.setEmail(user.getEmail());
        } else if (user.getEmail().equals(loggedUser.getEmail())) {
            loggedUser.setEmail(user.getEmail());
        } else if (user.getEmail() == null || user.getEmail().equals("")) {
            loggedUser.setEmail(email);
        } else throw new BadRequestException("Error: User with this already exist");

        loggedUser = userRepository.save(loggedUser);

        return new UserDTO("Profile successfully updated",
                loggedUser.getFirstName(),
                loggedUser.getLastName(),
                loggedUser.getEmail(),
                loggedUser.getGender(),
                loggedUser.getDateOfBirth(),
                loggedUser.getRoles());
    }

    public boolean checkIfValidOldPassword(User user,  UpdatePasswordRequest updatePasswordRequest){

        String newPassword = updatePasswordRequest.getNewPassword();
        String confirmNewPassword = updatePasswordRequest.getConfirmNewPassword();

        boolean passwordMatch = newPassword.equals(confirmNewPassword);

        boolean matches = bCryptPasswordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword());

        if(!passwordMatch||!matches){
            throw new BadRequestException("Passwords do not match");
        }

        return true;
    }

    @Override
    public boolean changeUserPassword(User user,  UpdatePasswordRequest updatePasswordRequest){

        String newPassword = updatePasswordRequest.getNewPassword();
        String confirmNewPassword = updatePasswordRequest.getConfirmNewPassword();

        if (newPassword.equals(confirmNewPassword)) {
            user.setPassword(bCryptPasswordEncoder.encode(updatePasswordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; email " + userId + " does not exist");
        return user.get();
    }

    @Override
    public UserDTO getUserDetails() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = findUserByEmail(email);
        return  UserDTO.build(user);
    }
}