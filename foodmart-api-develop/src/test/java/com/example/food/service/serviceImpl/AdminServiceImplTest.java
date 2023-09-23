package com.example.food.service.serviceImpl;

import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.AdminPasswordResetRequestDto;
import com.example.food.dto.EmailSenderDto;
import com.example.food.model.Order;
import com.example.food.model.Product;
import com.example.food.model.Users;
import com.example.food.pojos.ApplicationStatisticsResponse;
import com.example.food.pojos.CreateUserResponse;
import com.example.food.repositories.AdminPasswordResetTokenRepository;
import com.example.food.repositories.OrderRepository;
import com.example.food.repositories.ProductRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.restartifacts.BaseResponse;
import com.example.food.services.EmailService;
import com.example.food.services.serviceImpl.AdminServiceImpl;
import com.example.food.services.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdminServiceImplTest {
    @Mock
    private AdminPasswordResetTokenRepository adminPasswordResetTokenRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    private CustomUserDetailsService customUserDetailsService;
    @Mock
    EmailService emailService;
    @InjectMocks
    private AdminServiceImpl adminService;
    Users user1;
    Product product;
    AdminPasswordResetRequestDto adminPasswordResetRequestDto;
    Order order;
    String token = "newtoken";

    @BeforeEach
    void setup() {
        JwtUtil jwt = new JwtUtil();
        UserDetails mockedUserDetails = Mockito.mock(UserDetails.class);
        user1 = Users.builder()
                .email("okoro@gmail.com")
                .role(Role.ROLE_ADMIN).build();
        product = Product.builder()
                .productPrice(BigDecimal.valueOf(25))
                .productName("Electronics").build();
        order = Order.builder()
                .deliveryFee(BigDecimal.valueOf(25)).build();

        adminPasswordResetRequestDto = AdminPasswordResetRequestDto.builder().email("okoro@gmail.com").build();

        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo(adminPasswordResetRequestDto.getEmail());
        emailSenderDto.setSubject("Admin Password Reset Link");
        emailSenderDto.setContent("http://localhost:8080/admin/reset-password/" + token);

        when(jwt.generateToken(mockedUserDetails)).thenReturn(token);
//        when(userRepository.findByEmail((adminPasswordResetRequestDto.getEmail()))).thenReturn(Optional.ofNullable(user1));
        when(customUserDetailsService.loadUserByUsername(anyString())).thenReturn(mockedUserDetails);
    }
    @Test
    void adminRequestNewPassword() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user1));
        doNothing().when(emailService).sendMail(any(EmailSenderDto.class));

        BaseResponse response = adminService.adminRequestNewPassword(adminPasswordResetRequestDto);
        assertEquals(response.getDescription(), "Please check your email for a password reset link.");
    }
    @Test
    void displayAdminBasicInformation(){
        when(userRepository.findAllByRole(user1.getRole())).thenReturn(List.of(user1));
        CreateUserResponse baseResponse = adminService.displayAdminBasicInformation();
        assertEquals(baseResponse.getUsersBasicInformationDetails().size(), 1);
    }
    @Test
    void showApplicationStatistics(){
        when(orderRepository.findAll()).thenReturn(List.of(order));
        when(userRepository.findAll()).thenReturn(List.of(user1));
        when(productRepository.findAll()).thenReturn(List.of(product));
        ApplicationStatisticsResponse baseResponse = adminService.showApplicationStatistics();
        assertEquals(baseResponse.getApplicationDetails().getNumberOfRegisteredCustomer(), 1);
        assertEquals(baseResponse.getApplicationDetails().getNumberOfAvailableProduct(), 1);
    }
}
