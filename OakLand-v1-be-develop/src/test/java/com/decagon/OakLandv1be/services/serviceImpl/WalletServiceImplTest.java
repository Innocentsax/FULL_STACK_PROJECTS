//package com.decagon.OakLandv1be.services.serviceImpl;
//
//import com.decagon.OakLandv1be.dto.FundWalletRequest;
//import com.decagon.OakLandv1be.dto.FundWalletResponseDto;
//import com.decagon.OakLandv1be.entities.Customer;
//import com.decagon.OakLandv1be.entities.Person;
//import com.decagon.OakLandv1be.entities.Transaction;
//import com.decagon.OakLandv1be.entities.Wallet;
//import com.decagon.OakLandv1be.exceptions.UnauthorizedUserException;
//import com.decagon.OakLandv1be.repositries.PersonRepository;
//import com.decagon.OakLandv1be.repositries.TransactionRepository;
//import com.decagon.OakLandv1be.repositries.WalletRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class WalletServiceImplTest {
//
//    @Mock
//    private PersonRepository personRepository;
//
//    @Mock
//    private WalletRepository walletRepository;
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @Mock
//    private JavaMailServiceImpl mailService;
//
//    @InjectMocks
//    private WalletServiceImpl walletService;
//
//    @Test
//    void testFundWallet_success() throws IOException {
//        // Arrange
//        FundWalletRequest request = new FundWalletRequest(100.0);
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("test@example.com");
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        Person person = new Person();
//        person.setEmail("test@example.com");
//        person.setFirstName("Test");
//        person.setLastName("User");
//        when(personRepository.findByEmail("test@example.com")).thenReturn(Optional.of(person));
//
//        Wallet wallet = new Wallet();
//        wallet.setAccountBalance(BigDecimal.valueOf(100.0));
//        when(walletRepository.save(any(Wallet.class))).thenAnswer(i -> i.getArguments()[0]);
//        person.setCustomer(new Customer());
//        person.getCustomer().setWallet(wallet);
//
//        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        // Act
//        FundWalletResponseDto response = walletService.fundWallet(request);
//
//        // Assert
//        assertEquals(1100.0, response.getNewBalance());
//        assertEquals(100.0, response.getDepositAmount());
//        assertEquals("Test User", response.getFullName());
//        verify(walletRepository, times(1)).save(any(Wallet.class));
//        verify(transactionRepository, times(1)).save(any(Transaction.class));
//        verify(mailService, times(1)).sendMail(eq("test@example.com"),
//                eq("Wallet deposit"), anyString());
//    }
//
//    @Test
//    void testFundWallet_unauthorized() {
//        // Arrange
//        FundWalletRequest request = new FundWalletRequest(100.0);
//        Authentication authentication = mock(AnonymousAuthenticationToken.class);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Act and assert
//        assertThrows(UnauthorizedUserException.class, () -> walletService.fundWallet(request));
//    }
//}
//
