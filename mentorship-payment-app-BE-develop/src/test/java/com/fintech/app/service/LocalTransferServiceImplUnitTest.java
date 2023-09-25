package com.fintech.app.service;

import com.fintech.app.model.Transfer;
import com.fintech.app.model.User;
import com.fintech.app.model.Wallet;
import com.fintech.app.repository.TransferRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.repository.WalletRepository;
import com.fintech.app.request.LocalTransferRequest;
import com.fintech.app.service.impl.LocalTransferServiceImpl;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class LocalTransferServiceImplUnitTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransferRepository transferRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private User user1;
    private User user2;
    private Wallet wallet1;
    private Wallet wallet2;
    private Transfer localTransfer;

    @InjectMocks
    private LocalTransferServiceImpl localTransferService;


    @BeforeEach
    void setUp() {
        user1 = User.builder().firstName("Stan").lastName("Sender").email("stan@gmail.com").build();
        user2 = User.builder().firstName("Stan").lastName("Recipient").email("stan2@gmail.com").build();
        wallet1 = Wallet.builder().accountNumber("1234567890").user(user1).balance(10000.0).build();
        wallet2 = Wallet.builder().accountNumber("1234564890").user(user2).balance(2000.0).build();
        localTransfer = Transfer.builder().id(1L).senderFullName(user1.getFirstName() + " " + user1.getLastName())
                .senderAccountNumber(wallet1.getAccountNumber())
                .destinationFullName(user2.getFirstName() + " " + user2.getLastName())
                .destinationAccountNumber(wallet2.getAccountNumber())
                .amount(2000.0)
                .narration("Stan Sender")
                .status("SUCCESSFUL")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testSuccessfulLocalTransfer() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn(user1.getEmail());
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(wallet2);
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(user1);
        Mockito.when(walletRepository.findWalletByUser(any())).thenReturn(wallet1);
//        Mockito.when(walletRepository.findWalletByUser(eq(user2))).thenReturn(wallet2);
        Mockito.when(passwordEncoder.matches(any(), any())).thenReturn(true);
        Mockito.when(transferRepository.save(any())).thenReturn(localTransfer);
        var response = localTransferService.makeLocalTransfer(new LocalTransferRequest(
                "1234", 2000.0, wallet2.getAccountNumber(), "Stan Sender"
        ));
//        when(walletRepository.save(any())).thenReturn(null);
        Assertions.assertThat(response.getMessage()).isEqualTo("Transfer to " + user2.getFirstName() + " " + user2.getLastName() + " successful");
        Assertions.assertThat(wallet2.getBalance()).isEqualTo(4000.0);
        Assertions.assertThat(wallet1.getBalance()).isEqualTo(8000.0);
    }

    @Test
    void testNegativeTransferAmount() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(wallet2);
        Mockito.when(authentication.getName()).thenReturn(user1.getEmail());
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(user1);
        Mockito.when(walletRepository.findWalletByUser(any())).thenReturn(wallet1);
        var response = localTransferService.makeLocalTransfer(new LocalTransferRequest(
                "1234", -2000.0, wallet2.getAccountNumber(), "Stan Sender"
        ));
        Assertions.assertThat(response.getMessage()).isEqualTo("Invalid transfer amount");
    }

    @Test
    void testForInsufficientFunds() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(wallet2);
        Mockito.when(authentication.getName()).thenReturn(user1.getEmail());
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(user1);
        Mockito.when(walletRepository.findWalletByUser(any())).thenReturn(wallet1);
        var response = localTransferService.makeLocalTransfer(new LocalTransferRequest(
                "1234", 20000.0, wallet2.getAccountNumber(), "Stan Sender"
        ));
        Assertions.assertThat(response.getMessage()).isEqualTo("Insufficient funds");
    }

    @Test
    void testForIncorrectTransferPin() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(wallet2);
        Mockito.when(authentication.getName()).thenReturn(user1.getEmail());
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(user1);
        Mockito.when(walletRepository.findWalletByUser(any())).thenReturn(wallet1);
        var response = localTransferService.makeLocalTransfer(new LocalTransferRequest(
                "3335", 2000.0, wallet2.getAccountNumber(), "Stan Sender"
        ));
        Assertions.assertThat(response.getMessage()).isEqualTo("Incorrect transfer pin");
    }

    @Test
    void testResolveLocalAccountReturnsValidUserFullName() {
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(wallet2);
        var response = localTransferService.resolveLocalAccount("1234564890");
        Assertions.assertThat(response.getResult()).isEqualTo("Stan Recipient");
    }

    @Test
    void testResolveLocalAccountReturnsNotFound() {
        Mockito.when(walletRepository.findWalletByAccountNumber(anyString())).thenReturn(null);
        var response = localTransferService.resolveLocalAccount("1234564890");
        Assertions.assertThat(response.getMessage()).isEqualTo("Account not found");
    }
}