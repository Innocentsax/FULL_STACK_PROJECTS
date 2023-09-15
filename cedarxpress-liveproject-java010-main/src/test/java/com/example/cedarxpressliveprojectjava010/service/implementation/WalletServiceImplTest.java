package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.WalletDto;
import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.dto.request.WalletWithdrawalRequest;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.entity.WalletTransaction;
import com.example.cedarxpressliveprojectjava010.enums.Role;
import com.example.cedarxpressliveprojectjava010.enums.TransactionType;
import com.example.cedarxpressliveprojectjava010.exception.ClientRequestException;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletTransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletTransactionsRepository walletTransactionsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletServiceImpl walletService;

    private User user;

    private FundWalletRequest fundWalletRequest;


    private WalletWithdrawalRequest walletWithdrawalRequest;

    private Wallet wallet;

    private WalletTransaction walletTransaction;
    private User checkBalanceUser;
    private Wallet checkBalanceUserWallet;

    @BeforeEach
    void setUp() {
        checkBalanceUser = User.builder()
                .firstName("test first name")
                .lastName("test second name")
                .email("test@gmail.com")
                .build();

        checkBalanceUserWallet = Wallet.builder()
                .balance(BigDecimal.valueOf(20000.00))
                .build();

        user = new User();
        user.setEmail("chinekeebube@gmail.com");
        user.setRole(Role.ROLE_CUSTOMER);


        wallet = new Wallet();
        wallet.setUser(User.builder()
                .email("chinekeebube@gmail.com")
                .role(Role.ROLE_CUSTOMER)
                .build());


        fundWalletRequest = new FundWalletRequest();
        fundWalletRequest.setEmail("chinekeebube@gmail.com");
        fundWalletRequest.setAmount(new BigDecimal("800.0"));

        walletTransaction = new WalletTransaction();
        walletTransaction.setId(1L);
        walletTransaction.setWallet(wallet);
        walletTransaction.setTransactionType(TransactionType.FUNDWALLET);
        walletTransaction.setAmount(fundWalletRequest.getAmount());
        walletTransaction.setCreatedTime(LocalDateTime.now());
        walletTransaction.setModifiedTime(LocalDateTime.now());


        walletWithdrawalRequest = new WalletWithdrawalRequest();
        walletWithdrawalRequest.setAmount(new BigDecimal("100.0"));
        walletWithdrawalRequest.setEmail("chinekeebube@gmail.com");

        walletTransaction = new WalletTransaction();
        walletTransaction.setWallet(wallet);
        walletTransaction.setTransactionType(TransactionType.WITHDRAWAL);
        walletTransaction.setAmount(walletWithdrawalRequest.getAmount());
        walletTransaction.setCreatedTime(LocalDateTime.now());
        walletTransaction.setModifiedTime(LocalDateTime.now());


    }

    @Test
    void shouldBeAbleToFundWallet() {
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.ofNullable(user));

        when(userRepository.findUserByEmail(fundWalletRequest.getEmail())).thenReturn(Optional.ofNullable(user));
        when(walletRepository.findWalletByUserEmail(fundWalletRequest.getEmail())).thenReturn(Optional.ofNullable(wallet));
        when(walletRepository.save(wallet)).thenReturn(wallet);
        when(walletRepository.findWalletByUserEmail(fundWalletRequest.getEmail())).thenReturn(Optional.ofNullable(wallet));


        ResponseEntity<Wallet> walletResponseEntity = walletService.fundWallet(fundWalletRequest);

        assertThat(walletResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(walletResponseEntity.getBody().getUser().getRole()).isEqualTo(Role.ROLE_CUSTOMER);
        assertThat(walletResponseEntity.getBody().getBalance()).isNotNull();
        assertThat(walletResponseEntity.getBody().getBalance()).isEqualTo(fundWalletRequest.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenUserUserDoesNotHaveSufficientBalance() {

    WalletWithdrawalRequest walletWithdrawalRequest = new WalletWithdrawalRequest(
            "chinekeebube@gmail.com",
            new BigDecimal(200)
    );
    when(walletRepository.findWalletByUserEmail(walletWithdrawalRequest.getEmail())).thenReturn(Optional.ofNullable(wallet));
        assertThatThrownBy(() -> {
            walletService.walletWithdrawal(walletWithdrawalRequest);
        })
                .isInstanceOf(ClientRequestException.class)
                .hasMessageContaining("Insufficient funds");

    }


    @Test
    void shouldBeAbleToWithdrawFromBalance(){
        FundWalletRequest fundWalletRequest = new FundWalletRequest(
                "chinekeebube@gmail.com",
                new BigDecimal(1000)
        );

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(fundWalletRequest.getAmount());
        walletTransaction = new WalletTransaction();
        walletTransaction.setId(1L);
        walletTransaction.setWallet(wallet1);
        walletTransaction.setTransactionType(TransactionType.FUNDWALLET);
        walletTransaction.setAmount(fundWalletRequest.getAmount());
        walletTransaction.setCreatedTime(LocalDateTime.now());
        walletTransaction.setModifiedTime(LocalDateTime.now());

    WalletWithdrawalRequest walletWithdrawalRequest = new WalletWithdrawalRequest(
            "chinekeebube@gmail.com",
            new BigDecimal(101)
        );
        BigDecimal balance = wallet.getBalance();
        wallet.setBalance(balance.subtract(walletWithdrawalRequest.getAmount()));
        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setTransactionType(TransactionType.WITHDRAWAL);
        walletTransaction.setAmount(walletWithdrawalRequest.getAmount());
        walletTransaction.setCreatedTime(LocalDateTime.now());
        walletTransaction.setWallet(wallet);
        walletTransactionsRepository.save(walletTransaction);
        walletRepository.save(wallet);

        when(walletRepository.findWalletByUserEmail(walletWithdrawalRequest.getEmail())).thenReturn(Optional.ofNullable(wallet1));
        when(walletRepository.save(wallet1)).thenReturn(wallet1);

        ResponseEntity<Wallet> walletResponseEntity1 = walletService.walletWithdrawal(walletWithdrawalRequest);
        assertThat(walletResponseEntity1.getStatusCodeValue()).isEqualTo(200);
        assertThat(walletResponseEntity1.getBody().getBalance()).isNotNull();
        assertThat(walletWithdrawalRequest.getAmount()).isNotNull();
    }


    @Test
    @DisplayName("TEST: check wallet balance")
    void shouldCheckWalletBalance() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(checkBalanceUser));
        when(walletRepository.findWalletByUserEmail(checkBalanceUser.getEmail())).thenReturn(Optional.ofNullable(checkBalanceUserWallet));

        ResponseEntity<WalletDto> walletDtoResponseEntity = walletService.checkBalance(1L);
        assertThat(checkBalanceUserWallet.getBalance()).isEqualTo(walletDtoResponseEntity.getBody().getCurrentBalance());
    }


    @Test
    @DisplayName("TEST: user not found")
    void should_throw_UsernameNotFoundException(){
        assertThatThrownBy(() -> {
            walletService.checkBalance(2L);
        }).isInstanceOf(UsernameNotFoundException .class)
                .hasMessageContaining("user not found");
    }
}
