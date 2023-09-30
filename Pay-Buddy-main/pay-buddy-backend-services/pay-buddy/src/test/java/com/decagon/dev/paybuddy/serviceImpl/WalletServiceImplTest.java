package com.decagon.dev.paybuddy.serviceImpl;

import com.decagon.dev.paybuddy.dtos.responses.vtpass.request.BuyDataPlanRequest;
import com.decagon.dev.paybuddy.dtos.responses.vtpass.response.data.BuyDataPlanResponse;
import com.decagon.dev.paybuddy.exceptions.WalletServiceException;
import com.decagon.dev.paybuddy.models.Transaction;
import com.decagon.dev.paybuddy.models.User;
import com.decagon.dev.paybuddy.models.Wallet;
import com.decagon.dev.paybuddy.repositories.TransactionRepository;
import com.decagon.dev.paybuddy.repositories.UserRepository;
import com.decagon.dev.paybuddy.repositories.WalletRepository;
import com.decagon.dev.paybuddy.services.WalletService;
import com.decagon.dev.paybuddy.services.paystack.PayStackWithdrawalService;
import com.decagon.dev.paybuddy.services.paystack.PaystackPaymentService;
import com.decagon.dev.paybuddy.services.vtpass.VTPassService;
import com.decagon.dev.paybuddy.utilities.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Ikechi Ucheagwu
 * @created 09/03/2023 - 02:56
 * @project Pay-Buddy
 */

@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    private WalletService walletService;

    @Mock
    private PayStackWithdrawalService payStackWithdrawalService;
    @Mock
    private PaystackPaymentService paystackPaymentService;
    @Mock
    private VTPassService vtPassService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletRepository walletRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserUtil userUtil;
    private User user;
    private Wallet wallet;
    private BuyDataPlanRequest buyDataPlanRequest;
    private BuyDataPlanResponse buyDataPlanResponse;
    private final String testAccount = "test@admin.com";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        walletService = new WalletServiceImpl(
                payStackWithdrawalService,
                paystackPaymentService,
                vtPassService,
                userRepository,
                walletRepository,
                transactionRepository,
                userUtil,
                new BCryptPasswordEncoder()
        );

        buyDataPlanRequest = BuyDataPlanRequest.builder()
                .request_id("202303090250D12985346")
                .serviceID("mtn-data")
                .variation_code("mtn-10mb-100")
                .amount(new BigDecimal("100.00"))
                .phone("08011111111")
                .build();

        user = User.builder()
                .userId(1L)
                .email(testAccount)
                .build();

        wallet = Wallet.builder()
                .walletId(2L)
                .walletId(22L)
                .pin("$2a$10$ZU15WTP0T61mSAo0AxViduBL8psCgIznZqbmJCE5HhEGtKYaFqYnq") //1234
                .user(user)
                .accountBalance(new BigDecimal("150"))
                .build();

        buyDataPlanResponse = BuyDataPlanResponse.builder()
                .code("0000")
                .response_description("TRANSACTION SUCCESSFUL")
                .requestId("3476we129909djd")
                .amount("100.00")
                .purchasedCode("")
                .build();
    }


    @Test
    void getDataServices() {

    }

    @Test
    void getDataPlans() {

    }

    @Test
    void buyDataPlanSuccess() {
        setLoggedInUser();
        when(walletRepository.findWalletByUser_Email(testAccount)).thenReturn(wallet);
        when(vtPassService.payDataPlan(buyDataPlanRequest)).thenReturn(buyDataPlanResponse);

        walletService.buyDataPlan(buyDataPlanRequest, "1234");

        verify(walletRepository, times(1)).save(any(Wallet.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        assertEquals(new BigDecimal("50.00"), wallet.getAccountBalance());
    }

    @Test
    void buyDataPlanWrongPin() {
        setLoggedInUser();
        when(walletRepository.findWalletByUser_Email(testAccount)).thenReturn(wallet);

        WalletServiceException thrown = assertThrows(
                WalletServiceException.class, ()-> walletService.buyDataPlan(buyDataPlanRequest, "1235"));

        assertEquals("Pin is wrong", thrown.getMessage());
    }

    @Test
    void buyDataPlanInSufficientFund() {
        buyDataPlanRequest.setVariation_code("mtn-50mb-200");
        buyDataPlanRequest.setAmount(new BigDecimal("200"));
        setLoggedInUser();
        when(walletRepository.findWalletByUser_Email(testAccount)).thenReturn(wallet);

        WalletServiceException thrown = assertThrows(
                WalletServiceException.class, ()-> walletService.buyDataPlan(buyDataPlanRequest, "1234"));

        assertEquals("Insufficient balance", thrown.getMessage());
    }

    private void setLoggedInUser() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(testAccount);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
    }
}