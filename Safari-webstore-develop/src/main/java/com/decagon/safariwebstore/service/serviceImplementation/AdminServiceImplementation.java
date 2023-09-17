package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.dto.ProductDTO;
import com.decagon.safariwebstore.exceptions.BadRequestException;
import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.*;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.payload.response.auth.ResetPassword;
import com.decagon.safariwebstore.repository.ProductRepository;
import com.decagon.safariwebstore.repository.RoleRepository;
import com.decagon.safariwebstore.service.AdminService;
import com.decagon.safariwebstore.service.UserService;
import com.decagon.safariwebstore.utils.DateUtils;
import com.decagon.safariwebstore.utils.MethodUtils;
import com.decagon.safariwebstore.utils.mailService.MailService;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImplementation implements AdminService {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    /**
     * Sends an email to the admin with the url link to reset password
     * @param req
     * @param email
     * */
    @Override
    public ResponseEntity<Response> adminForgotPassword(HttpServletRequest req, String email) {
        // Lookup user in database by e-mail
        Optional<User> adminOptional = userService.getUserByEmail(email);

        //response handler
        Response res = new Response();

        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));

        if(adminOptional.isEmpty()) {
            res.setStatus(404);
            res.setMessage("We couldn't find an account with that e-mail address.");

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        Role role = adminOptional.get().getRoles().get(0);

        if(role != adminRole){
            res.setStatus(401);
            res.setMessage("You don't have access to this link");

            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }

        try{
            // Generate random 36-character string token for reset password
            User admin = adminOptional.get();

            admin.setPasswordResetToken(UUID.randomUUID().toString());

            //24hours expiry date for token
            String tokenExpiryDate = DateUtils.passwordResetExpiryTimeLimit();

            admin.setPasswordResetExpireDate(tokenExpiryDate);

            String appUrl = req.getScheme() + "://" + req.getServerName();

            String subject = "Admin Reset Password";

            String mailBody = "To reset your password, click the link below:\n"
                    + appUrl + "/reset?token="
                    + admin.getPasswordResetToken();

            mailService.sendMessage(admin.getEmail(), subject, mailBody);

            // Save token and expiring date to database
            userService.saveUser(admin);

            res.setStatus(200);
            res.setMessage("Successfully sent email");

        }catch (UnirestException e){
            System.out.println("Error sending email:\n\tError message:"+e.getMessage());
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * This method check the validity of the token sent and also validates passwords(password and confirm password)
     * before updating it
     * @param resetPassword
     * @return response
     * */
    @Override
    public ResponseEntity<Response> adminResetPassword(ResetPassword resetPassword) {

        //find the admin by the token
        Optional<User> adminOptional = userService.findUserByResetToken(resetPassword.getToken());

        String password = resetPassword.getPassword();
        String confirmPassword = resetPassword.getConfirmPassword();

        //response handler
        Response res = new Response();

        if (adminOptional.isEmpty()){
            res.setStatus(400);
            res.setMessage("Oops!  This is an invalid password reset link.");

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        User admin = adminOptional.get();

        if(!password.equals(confirmPassword)){
            res.setStatus(400);
            res.setMessage("Passwords does not match");

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        //set the encrypted password
        admin.setPassword(bCryptPasswordEncoder.encode(password));

        // Set the reset token to null, so it cannot be used again
        admin.setPasswordResetToken(null);

        //set the reset passwordRestExpireDate to null
        admin.setPasswordResetExpireDate(null);


        try {
            // Save person
            userService.saveUser(admin);

            res.setStatus(201);
            res.setMessage("You have successfully reset your password. You can now login.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Override
    @Cacheable(cacheNames = "products", sync = true)
    public ResponseEntity<Page<ProductDTO>> getAllProducts(ProductPage adminProductPage) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {

            Pageable pageable = MethodUtils.getPageable(adminProductPage);
            Page<Product> products = productRepository.findAll(pageable);
            List<ProductDTO> productList = getAdminProductList(products);
            Page<ProductDTO> productDTOPage = new PageImpl<>(productList);

            return new ResponseEntity<>(productDTOPage, HttpStatus.OK);

        }else {
            throw new BadRequestException("you do not have permission to view this list");
        }

    }

    @Override
    @Cacheable(cacheNames = "products", sync = true)
    public ResponseEntity<Product> getSingleProduct(Long productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {

            Product aProduct = productRepository.findById(productId).orElseThrow(
                    ()-> new ResourceNotFoundException("This product is not available"));
            return new ResponseEntity<>(aProduct, HttpStatus.OK);
        }else {
            throw new BadRequestException("you do not have permission to view this product");
        }
    }



    private List<ProductDTO> getAdminProductList(Page<Product> products) {

            return products.stream()
                    .map(product -> modelMapper.map(product, ProductDTO.class))
                    .collect(Collectors.toList());
    }
}

