package com.fintech.app.service;

import com.fintech.app.model.User;
import com.fintech.app.model.Wallet;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.response.BaseResponse;
import com.fintech.app.response.WalletResponse;
import com.fintech.app.service.impl.WalletServiceImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class FetchUserWalletUnitTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    private User user;
    private Wallet wallet;

    @InjectMocks
    private WalletServiceImpl walletService;

    @BeforeEach
    void  setUp(){
        user = User.builder()
                .firstName("Stanley")
                .lastName("Gabriel")
                .email("stan@gmail.com")
                .build();

        wallet = Wallet.builder()
                .accountNumber("0012345678")
                .user(user)
                .bankName("Access")
                .createdAt(LocalDateTime.now())
                .modifyAt(LocalDateTime.now())
                .build();
    }

    @Test
    void fetchUserWallet() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());
        given(userRepository.findUserByEmail(any())).willReturn(user);
        when(walletRepository.findWalletByUser(any())).thenReturn(wallet);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E, dd-MMMM-yyyy HH:mm");
        BaseResponse<WalletResponse> response = walletService.fetchUserWallet();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);

    }

    @Test
    void testForWhenUserIsNotLoggedIn(){
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(any())).thenReturn(user);
        when(userRepository.findUserByEmail(any())).thenReturn(null);
        BaseResponse<WalletResponse> response = walletService.fetchUserWallet();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}