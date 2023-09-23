package com.example.food.service.serviceImpl;

import com.example.food.Enum.ResponseCodeEnum;
import com.example.food.model.Users;
import com.example.food.model.Wallet;
import com.example.food.pojos.WalletResponse;
import com.example.food.repositories.UserRepository;
import com.example.food.repositories.WalletRepository;
import com.example.food.services.serviceImpl.WalletServiceImpl;
import com.example.food.util.ResponseCodeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    ResponseCodeUtil responseCodeUtil = new ResponseCodeUtil();
    @InjectMocks
    private WalletServiceImpl walletServiceImpl;

    WalletResponse walletResponse = new WalletResponse();

    @Test
    public void testGetWalletBalance() {
        // setup
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("test@test.com", "password"));

        walletResponse.setCode(0);
        walletResponse.setDescription("Wallet Balance");
        walletResponse.setWalletBalance(new BigDecimal(10000));

        Wallet wallet = Wallet.builder()
                .walletBalance(new BigDecimal(10_000))
                .build();
        Users user = Users.builder()
                .email("test@test.com")
                .password("password")
                .firstName("test")
                .lastName("user")
                .wallet(wallet)
                .build();

        when(walletRepository.findWalletByUser_Email(anyString())).thenReturn(wallet);
        Mockito.when(userRepository.findByEmail( any())).thenReturn(Optional.ofNullable(user));
        // test
        WalletResponse response = walletServiceImpl.getWalletBalance();
        // assertions
        assertEquals(ResponseCodeEnum.SUCCESS.getCode(), response.getCode());
        assertEquals("Wallet Balance", response.getDescription());
        assertEquals(new BigDecimal("10000"), response.getWalletBalance());
    }
}
