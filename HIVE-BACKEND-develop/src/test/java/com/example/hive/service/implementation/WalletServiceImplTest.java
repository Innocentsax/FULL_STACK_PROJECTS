//package com.example.hive.service.implementation;
//
//import com.example.hive.constant.TransactionType;
//import com.example.hive.entity.Task;
//import com.example.hive.entity.TransactionLog;
//import com.example.hive.entity.User;
//import com.example.hive.entity.Wallet;
//import com.example.hive.enums.Role;
//import com.example.hive.enums.Status;
//import com.example.hive.repository.TaskRepository;
//import com.example.hive.repository.TransactionLogRepository;
//import com.example.hive.repository.UserRepository;
//import com.example.hive.repository.WalletRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//class WalletServiceImplTest {
//
//    @Autowired
//    private WalletServiceImpl walletService;
//
//    @MockBean
//    private WalletRepository walletRepository;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private TransactionLogRepository transactionLogRepository;
//    @Autowired
//    @MockBean
//    private TaskRepository taskRepository;
//
//    private User doer = new User();
//    private User tasker = new User();
//    private Wallet wallet= new Wallet();
//    private TransactionLog transactionLog = new TransactionLog();
//    private Task task= new Task();
//
//    @BeforeEach
//    public void setUp() {
//        // Create a user
//        doer.setUser_id(UUID.randomUUID());
//        doer.setFullName("John Doe");
//        doer.setRole(Role.DOER);
////create a tasker
//
//        tasker.setUser_id(UUID.randomUUID());
//        tasker.setFullName("Mary Jane");
//        tasker.setRole(Role.TASKER);
//
//
//        //create a task
//        task.setTask_id(UUID.randomUUID());
//        task.setJobType("Logistics");
//        task.setTaskDescription("Get me food from The Place");
//        task.setBudgetRate(new BigDecimal("100"));
//        task.setTaskAddress("The Place Restaurant");
//        task.setTaskDeliveryAddress("7 Asajon Way, Sangotedo");
//        task.setEstimatedTime(1);
//        task.setDoer(doer);
//        task.setTasker(tasker);
//        task.setTaskDuration(LocalDateTime.now());
//        task.setStatus(Status.COMPLETED);
//
//        // Create a wallet
//        wallet.setWallet_id(UUID.randomUUID().toString());
//        wallet.setUser(doer);
//
//        //create a transaction log
//
//        transactionLog.setTransactionId(UUID.randomUUID().toString());
//        transactionLog.setTransactionType(TransactionType.DEPOSIT);
//        transactionLog.setDoerWithdrawer(doer);
//        transactionLog.setAmount(new BigDecimal("100.00"));
//    }
//
//    @Test
//    public void shouldCreditDoerWalletTest() {
//
//        // Mock the repository methods
//        Mockito.when(userRepository.findById(doer.getUser_id())).thenReturn(Optional.of(doer));
//        Mockito.when(walletRepository.findByUser(doer)).thenReturn(Optional.of(wallet));
//        Mockito.when(transactionLogRepository.findById(transactionLog.getTransactionId())).thenReturn(Optional.of(transactionLog));
//        Mockito.when(taskRepository.findById(task.getTask_id())).thenReturn(Optional.of(task));
//        // Call the creditDoerWallet method
//        BigDecimal creditAmount = new BigDecimal("100.00");
//
//
//        // Verify the result
//        assertTrue(result);
//        assertEquals(creditAmount, wallet.getAccountBalance());
//
//        // Verify the repository methods were called
//        Mockito.verify(userRepository, Mockito.times(1)).findById(doer.getUser_id());
//        Mockito.verify(walletRepository, Mockito.times(1)).findByUser(doer);
//        Mockito.verify(userRepository, Mockito.times(1)).save(doer);
//    }
//
//}