package com.example.food.services.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.AdminPasswordResetDto;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.PasswordResetTokenEntity;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.ApplicationDetails;
import com.example.food.pojos.ApplicationStatisticsResponse;
import com.example.food.pojos.CreateUserResponse;
import com.example.food.pojos.UsersBasicInformationDetails;
import com.example.food.repositories.AdminPasswordResetTokenRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.AdminService;
import com.example.food.services.EmailService;
import com.example.food.util.ResponseCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final PasswordEncoder passwordEncoder;
    private final AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final EmailService emailService;
    private final ProductRepository productRepository;
    private final ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();

    public static  Users unwrapUsers(Optional<Users> users) {
        if (users.isPresent())
            return users.get();
        throw new RuntimeException("User not found");
    }
    @Override
    public BaseResponse adminRequestNewPassword(AdminPasswordResetRequestDto adminPasswordResetRequestDto) {
        Optional<Users> users = userRepository.findByEmail(adminPasswordResetRequestDto.getEmail());
        Users adminUser = unwrapUsers(users);
        BaseResponse baseResponse = new BaseResponse();

        //Check if adminUser is registered and has admin role, then create token and assign it to adminUser
        if (Role.ROLE_ADMIN != adminUser.getRole()) {
            return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_RESETTING_PASSWORD);
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(adminPasswordResetRequestDto.getEmail());
        String token = new JwtUtil().generateToken(userDetails);

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUsersDetails(adminUser);
        adminPasswordResetTokenRepository.save(passwordResetTokenEntity);

        //After creating and assigning token to adminUser, send token via email to adminUser
        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(adminPasswordResetRequestDto.getEmail());
        emailSenderDto.setSubject("Admin Password Reset Link");
        emailSenderDto.setContent("http://localhost:8080/admin/reset-password/" + token);
        emailService.sendMail(emailSenderDto);

        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS,
                "Please check your email for a password reset link.");
    }

    @Override
    public BaseResponse adminResetPassword(AdminPasswordResetDto adminPasswordResetDto) {
        JwtUtil jwtUtil1 = new JwtUtil();
        String email = jwtUtil1.extractUsername(adminPasswordResetDto.getToken());
        Optional<Users> users = userRepository.findByEmail(email);
        BaseResponse baseResponse = new BaseResponse();

        if (users.isPresent()) {
            PasswordResetTokenEntity passwordResetTokenEntity = adminPasswordResetTokenRepository.findByToken(adminPasswordResetDto.getToken());
            if (passwordResetTokenEntity != null) {
                String encodePassword = passwordEncoder.encode(adminPasswordResetDto.getPassword());
                Users user = passwordResetTokenEntity.getUsersDetails();
                user.setPassword(encodePassword);
                userRepository.save(user);
                adminPasswordResetTokenRepository.delete(passwordResetTokenEntity);
                return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.SUCCESS, "Password reset was successful.");
            }
        }
        return responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR_RESETTING_PASSWORD);
    }

    @Override
    public CreateUserResponse displayAdminBasicInformation() {
        CreateUserResponse baseResponse = new CreateUserResponse();
        List<Users> usersThatAreAdmin = userRepository.findAllByRole(Role.ROLE_ADMIN);
        if (usersThatAreAdmin.isEmpty())
            responseCodeUtil.updateResponseData(baseResponse, ResponseCodeEnum.ERROR, "No record found");

        List<UsersBasicInformationDetails> adminsBasicDetailsList = usersThatAreAdmin.stream()
                .map(eachAdminDetails -> UsersBasicInformationDetails.builder()
                        .firstName(eachAdminDetails.getFirstName())
                        .lastName(eachAdminDetails.getLastName())
                        .email(eachAdminDetails.getEmail())
                        .build()).collect(Collectors.toList());
        baseResponse.setUsersBasicInformationDetails(adminsBasicDetailsList);

        return responseCodeUtil.updateResponseData(baseResponse,ResponseCodeEnum.SUCCESS);
    }

    @Override
    public ApplicationStatisticsResponse showApplicationStatistics() {
        ApplicationStatisticsResponse applicationStatisticsResponse = new ApplicationStatisticsResponse();
        List<Users> numberOfRegisteredUser = userRepository.findAll();
        //List<Order> numberOfOder = orderRepository.findAll();
        List<Product>numberOfAvailableProduct = productRepository.findAll();
        ApplicationDetails applicationDetails = ApplicationDetails.builder()
                .numberOfRegisteredCustomer(numberOfRegisteredUser.size())
                .numberOfAvailableProduct(numberOfAvailableProduct.size())
                //.numberOfOrders(numberOfOder.size())
                .build();
        applicationStatisticsResponse.setApplicationDetails(applicationDetails);
        return responseCodeUtil.updateResponseData(applicationStatisticsResponse, ResponseCodeEnum.SUCCESS);

    }
}
