package com.example.food.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.food.Enum.Role;
import com.example.food.configurations.security.CustomUserDetailsService;
import com.example.food.configurations.security.JwtUtil;
import com.example.food.dto.LoginRequestDto;
import com.example.food.exceptions.UserNotFoundException;
import com.example.food.model.Cart;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.LoginResponseDto;
import com.example.food.repositories.CartRepository;
import com.example.food.repositories.PasswordResetTokenRepository;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.services.EmailService;
import com.example.food.util.AppUtil;
import com.example.food.util.UserUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private AppUtil appUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private EmailService emailService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UserUtil userUtil;

    @MockBean
    private WalletRepository walletRepository;

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDto)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<>());
        cart.setCartTotal(null);
        cart.setId(123L);
        cart.setQuantity(1);
        cart.setUsers(new Users());

        Wallet wallet = new Wallet();
        wallet.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        wallet.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        wallet.setUser(new Users());
        wallet.setWalletBalance(null);
        wallet.setWalletTransactions(new ArrayList<>());

        Users users = new Users();
        users.setAddress(new ArrayList<>());
        users.setBaseCurrency("GBP");
        users.setCart(cart);
        users.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setDateOfBirth(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        users.setEmail("jane.doe@example.org");
        users.setFavourites(new ArrayList<>());
        users.setFirstName("Jane");
        users.setId(123L);
        users.setIsActive(true);
        users.setLastName("Doe");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setModifiedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        users.setOrder(new ArrayList<>());
        users.setPassword("iloveyou");
        users.setRole(Role.ROLE_ADMIN);
        users.setWallet(wallet);

        Cart cart1 = new Cart();
        cart1.setCartItemList(new ArrayList<>());
        cart1.setCartTotal(BigDecimal.valueOf(42L));
        cart1.setId(123L);
        cart1.setQuantity(1);
        cart1.setUsers(users);

        Cart cart2 = new Cart();
        cart2.setCartItemList(new ArrayList<>());
        cart2.setCartTotal(null);
        cart2.setId(123L);
        cart2.setQuantity(1);
        cart2.setUsers(new Users());

        Wallet wallet1 = new Wallet();
        wallet1.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet1.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        wallet1.setId(123L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet1.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        wallet1.setUser(new Users());
        wallet1.setWalletBalance(null);
        wallet1.setWalletTransactions(new ArrayList<>());

        Users users1 = new Users();
        users1.setAddress(new ArrayList<>());
        users1.setBaseCurrency("GBP");
        users1.setCart(cart2);
        users1.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setCreatedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setDateOfBirth(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        users1.setEmail("jane.doe@example.org");
        users1.setFavourites(new ArrayList<>());
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setIsActive(true);
        users1.setLastName("Doe");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        users1.setOrder(new ArrayList<>());
        users1.setPassword("iloveyou");
        users1.setRole(Role.ROLE_ADMIN);
        users1.setWallet(wallet1);

        Wallet wallet2 = new Wallet();
        wallet2.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        wallet2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        wallet2.setUser(users1);
        wallet2.setWalletBalance(BigDecimal.valueOf(42L));
        wallet2.setWalletTransactions(new ArrayList<>());

        Users users2 = new Users();
        users2.setAddress(new ArrayList<>());
        users2.setBaseCurrency("GBP");
        users2.setCart(cart1);
        users2.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setDateOfBirth(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        users2.setEmail("jane.doe@example.org");
        users2.setFavourites(new ArrayList<>());
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setIsActive(true);
        users2.setLastName("Doe");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setModifiedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        users2.setOrder(new ArrayList<>());
        users2.setPassword("iloveyou");
        users2.setRole(Role.ROLE_ADMIN);
        users2.setWallet(wallet2);
        Optional<Users> ofResult = Optional.of(users2);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(jwtUtil.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(customUserDetailsService.loadUserByUsername((String) any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPassword("iloveyou");

        // Act
        ResponseEntity<LoginResponseDto> actualLoginResult = userServiceImpl.login(loginRequestDto);

        // Assert
        assertTrue(actualLoginResult.hasBody());
        assertTrue(actualLoginResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualLoginResult.getStatusCode());
        LoginResponseDto body = actualLoginResult.getBody();
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("ABC123", body.getToken());
        assertEquals("Jane", body.getFirstname());
        assertEquals("Doe", body.getLastName());
        verify(userRepository).findByEmail((String) any());
        verify(jwtUtil).generateToken((UserDetails) any());
        verify(customUserDetailsService).loadUserByUsername((String) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#login(LoginRequestDto)}
     */
    @Test
    void testLogin2() throws AuthenticationException {
        // Arrange
        Cart cart = new Cart();
        cart.setCartItemList(new ArrayList<>());
        cart.setCartTotal(null);
        cart.setId(123L);
        cart.setQuantity(1);
        cart.setUsers(new Users());

        Wallet wallet = new Wallet();
        wallet.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        wallet.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        wallet.setUser(new Users());
        wallet.setWalletBalance(null);
        wallet.setWalletTransactions(new ArrayList<>());

        Users users = new Users();
        users.setAddress(new ArrayList<>());
        users.setBaseCurrency("GBP");
        users.setCart(cart);
        users.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setDateOfBirth(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        users.setEmail("jane.doe@example.org");
        users.setFavourites(new ArrayList<>());
        users.setFirstName("Jane");
        users.setId(123L);
        users.setIsActive(true);
        users.setLastName("Doe");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users.setModifiedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        users.setOrder(new ArrayList<>());
        users.setPassword("iloveyou");
        users.setRole(Role.ROLE_ADMIN);
        users.setWallet(wallet);

        Cart cart1 = new Cart();
        cart1.setCartItemList(new ArrayList<>());
        cart1.setCartTotal(BigDecimal.valueOf(42L));
        cart1.setId(123L);
        cart1.setQuantity(1);
        cart1.setUsers(users);

        Cart cart2 = new Cart();
        cart2.setCartItemList(new ArrayList<>());
        cart2.setCartTotal(null);
        cart2.setId(123L);
        cart2.setQuantity(1);
        cart2.setUsers(new Users());

        Wallet wallet1 = new Wallet();
        wallet1.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet1.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        wallet1.setId(123L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet1.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        wallet1.setUser(new Users());
        wallet1.setWalletBalance(null);
        wallet1.setWalletTransactions(new ArrayList<>());

        Users users1 = new Users();
        users1.setAddress(new ArrayList<>());
        users1.setBaseCurrency("GBP");
        users1.setCart(cart2);
        users1.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setCreatedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setDateOfBirth(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        users1.setEmail("jane.doe@example.org");
        users1.setFavourites(new ArrayList<>());
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setIsActive(true);
        users1.setLastName("Doe");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        users1.setOrder(new ArrayList<>());
        users1.setPassword("iloveyou");
        users1.setRole(Role.ROLE_ADMIN);
        users1.setWallet(wallet1);

        Wallet wallet2 = new Wallet();
        wallet2.setBankDetails(new ArrayList<>());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        wallet2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        wallet2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        wallet2.setUser(users1);
        wallet2.setWalletBalance(BigDecimal.valueOf(42L));
        wallet2.setWalletTransactions(new ArrayList<>());

        Users users2 = new Users();
        users2.setAddress(new ArrayList<>());
        users2.setBaseCurrency("GBP");
        users2.setCart(cart1);
        users2.setConfirmationToken("ABC123");
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setDateOfBirth(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        users2.setEmail("jane.doe@example.org");
        users2.setFavourites(new ArrayList<>());
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setIsActive(true);
        users2.setLastName("Doe");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        users2.setModifiedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        users2.setOrder(new ArrayList<>());
        users2.setPassword("iloveyou");
        users2.setRole(Role.ROLE_ADMIN);
        users2.setWallet(wallet2);
        Optional<Users> ofResult = Optional.of(users2);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(jwtUtil.generateToken((UserDetails) any())).thenReturn("ABC123");
        when(customUserDetailsService.loadUserByUsername((String) any()))
                .thenReturn(new User("janedoe", "iloveyou", new ArrayList<>()));
        when(authenticationManager.authenticate((Authentication) any()))
                .thenThrow(new UserNotFoundException("An error occurred"));

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("jane.doe@example.org");
        loginRequestDto.setPassword("iloveyou");

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.login(loginRequestDto));
        verify(authenticationManager).authenticate((Authentication) any());
    }
}

