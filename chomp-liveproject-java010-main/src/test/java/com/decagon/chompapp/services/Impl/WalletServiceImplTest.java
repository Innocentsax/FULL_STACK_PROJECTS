package com.decagon.chompapp.services.Impl;


import com.decagon.chompapp.dtos.WalletTransactionRequest;
import com.decagon.chompapp.enums.TransactionType;
import com.decagon.chompapp.exceptions.InsufficientFundsException;
import com.decagon.chompapp.models.User;
import com.decagon.chompapp.models.Wallet;
import com.decagon.chompapp.models.WalletTransaction;
import com.decagon.chompapp.repositories.UserRepository;
import com.decagon.chompapp.repositories.WalletRepository;
import com.decagon.chompapp.repositories.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletTransactionRepository walletTransactionsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private User user;
    private WalletTransactionRequest walletTransactionRequest;
    private Wallet wallet;
    private WalletTransaction walletTransaction;

    @Test
    void shouldBeAbleToFundWallet() {

        user = User.builder()
                .userId(1L)
                .email("james@mail.com")
                .password("password")
                .username("james")
                .firstName("James")
                .build();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

        wallet = Wallet.builder()
                .walletBalance(2500)
                .build();

        wallet = new Wallet();
        wallet.setUser(User.builder()
                .email("james@mail.com")
                .build());

        walletTransactionRequest = new WalletTransactionRequest();
        walletTransactionRequest.setAmount(50000);

        walletTransaction = new WalletTransaction();
        walletTransaction.setWalletTransactionId(1L);
        walletTransaction.setWallet(wallet);
        walletTransaction.setTransactionType(TransactionType.FUNDWALLET);
        walletTransaction.setAmount(walletTransactionRequest.getAmount());
        walletTransaction.setTransactionDate(Date.valueOf(LocalDate.now()));
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(userDetails.getUsername());

        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUser_Email(user.getEmail())).thenReturn(wallet);
        when(walletRepository.save(wallet)).thenReturn(wallet);
        when(walletRepository.findWalletByUser_Email(user.getEmail())).thenReturn(wallet);

        ResponseEntity<String> walletResponseEntity = walletService.fundWalletAccount(walletTransactionRequest);

        assertThat(walletResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(wallet.getWalletBalance()).isNotNull();
        assertThat(wallet.getWalletBalance()).isEqualTo(walletTransactionRequest.getAmount());

    }

    @Test
    void testToWithdrawFromWallet() throws InsufficientFundsException {
        user = User.builder()
                .userId(1L)
                .email("adekunle@gmail.com")
                .password("12345")
                .username("kay")
                .firstName("James")
                .build();

        WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
        walletTransactionRequest.setAmount(2000.0);
        Wallet wallet = new Wallet();
        wallet.setWalletBalance(3000.0);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(userDetails.getUsername());
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUser_Email(user.getEmail())).thenReturn(wallet);

        ResponseEntity<String> wallet1 = walletService.withdrawFromWallet(walletTransactionRequest);

        assertThat(wallet1.getStatusCodeValue()).isEqualTo(200);
        assertThat(wallet.getWalletBalance()).isNotNull();
        Assertions.assertThat(wallet.getWalletBalance()).isEqualTo(1000.0);
    }

    @Test
    void testWalletBalance() {
        user = User.builder()
                .userId(1L)
                .email("adekunle@gmail.com")
                .password("12345")
                .username("kay")
                .firstName("James")
                .build();

        WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
        Wallet wallet = new Wallet();
        wallet.setWalletBalance(3000.0);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(userDetails.getUsername());
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUser_Email2(user.getEmail())).thenReturn(Optional.of(wallet));


        ResponseEntity<String> wallet1 = walletService.checkWalletBalance();

        assertThat(wallet1.getStatusCodeValue()).isEqualTo(200);
        assertThat(wallet.getWalletBalance()).isNotNull();
        Assertions.assertThat(wallet.getWalletBalance()).isEqualTo(3000.0);
    }

    @Test
    void testToProcessPayment() throws InsufficientFundsException{
        user = User.builder()
                .userId(1L)
                .email("dennis@gmail.com")
                .password("12345")
                .username("donDen")
                .firstName("okoye")
                .build();

        WalletTransactionRequest walletTransactionRequest = new WalletTransactionRequest();
        walletTransactionRequest.setAmount(5000.0);
        Wallet wallet = new Wallet();
        wallet.setWalletBalance(30000.0);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("PREMIUM")));

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(userDetails.getUsername());
        when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUser_Email(user.getEmail())).thenReturn(wallet);

        ResponseEntity<String> wallet1 = walletService.processPayment(walletTransactionRequest);

        assertThat(wallet1.getStatusCodeValue()).isEqualTo(200);
        assertThat(wallet.getWalletBalance()).isNotNull();
        assertThat(wallet.getWalletBalance()).isEqualTo(25000.0);
    }


}
