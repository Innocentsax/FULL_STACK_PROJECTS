package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ConfirmationTokenServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConfirmationTokenServiceImplTest {
    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;


    @Test
    void testSaveConfirmationToken() {
        Wallet wallet = new Wallet();
        wallet.setAcctNumber("42");
        wallet.setBalance(10.0d);
        wallet.setBankName("Bank Name");
        wallet.setCreatedAt(null);
        wallet.setId(123L);
        wallet.setTransactionList(new ArrayList<>());
        wallet.setUpdatedAt(null);
        wallet.setUsers(new Users());

        Users users = new Users();
        users.setBvn("Bvn");
        users.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setTransactionPin("Transaction Pin");
        users.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users.setUserStatus(UserStatus.ACTIVE);
        users.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setAcctNumber("42");
        wallet1.setBalance(10.0d);
        wallet1.setBankName("Bank Name");
        wallet1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setId(123L);
        wallet1.setTransactionList(new ArrayList<>());
        wallet1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet1.setUsers(users);

        Users users1 = new Users();
        users1.setBvn("Bvn");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setTransactionPin("Transaction Pin");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUserStatus(UserStatus.ACTIVE);
        users1.setWallet(wallet1);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUsers(users1);
        when(confirmationTokenRepository.save((ConfirmationToken) any())).thenReturn(confirmationToken);

        Users users2 = new Users();
        users2.setBvn("Bvn");
        users2.setCreatedAt(null);
        users2.setEmail("jane.doe@example.org");
        users2.setFirstName("Jane");
        users2.setId(123L);
        users2.setLastName("Doe");
        users2.setPassword("iloveyou");
        users2.setPhoneNumber("4105551212");
        users2.setTransactionPin("Transaction Pin");
        users2.setUpdatedAt(null);
        users2.setUserStatus(UserStatus.ACTIVE);
        users2.setWallet(new Wallet());

        Wallet wallet2 = new Wallet();
        wallet2.setAcctNumber("42");
        wallet2.setBalance(10.0d);
        wallet2.setBankName("Bank Name");
        wallet2.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setId(123L);
        wallet2.setTransactionList(new ArrayList<>());
        wallet2.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet2.setUsers(users2);

        Users users3 = new Users();
        users3.setBvn("Bvn");
        users3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setEmail("jane.doe@example.org");
        users3.setFirstName("Jane");
        users3.setId(123L);
        users3.setLastName("Doe");
        users3.setPassword("iloveyou");
        users3.setPhoneNumber("4105551212");
        users3.setTransactionPin("Transaction Pin");
        users3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users3.setUserStatus(UserStatus.ACTIVE);
        users3.setWallet(wallet2);

        ConfirmationToken confirmationToken1 = new ConfirmationToken();
        confirmationToken1.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken1.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken1.setId(123L);
        confirmationToken1.setToken("ABC123");
        confirmationToken1.setUsers(users3);
        confirmationTokenServiceImpl.saveConfirmationToken(confirmationToken1);
        verify(confirmationTokenRepository).save((ConfirmationToken) any());
        assertSame(users3, confirmationToken1.getUsers());
        assertEquals("01:01", confirmationToken1.getConfirmedAt().toLocalTime().toString());
        assertEquals(123L, confirmationToken1.getId().longValue());
        assertEquals("ABC123", confirmationToken1.getToken());
        assertEquals("0001-01-01", confirmationToken1.getCreatedAt().toLocalDate().toString());
        assertEquals("0001-01-01", confirmationToken1.getExpiresAt().toLocalDate().toString());
    }


    @Test
    void testGetToken() {
        Users users = new Users();
        users.setBvn("Bvn");
        users.setCreatedAt(null);
        users.setEmail("jane.doe@example.org");
        users.setFirstName("Jane");
        users.setId(123L);
        users.setLastName("Doe");
        users.setPassword("iloveyou");
        users.setPhoneNumber("4105551212");
        users.setTransactionPin("Transaction Pin");
        users.setUpdatedAt(null);
        users.setUserStatus(UserStatus.ACTIVE);
        users.setWallet(new Wallet());

        Wallet wallet = new Wallet();
        wallet.setAcctNumber("42");
        wallet.setBalance(10.0d);
        wallet.setBankName("Bank Name");
        wallet.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setId(123L);
        wallet.setTransactionList(new ArrayList<>());
        wallet.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet.setUsers(users);

        Users users1 = new Users();
        users1.setBvn("Bvn");
        users1.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setEmail("jane.doe@example.org");
        users1.setFirstName("Jane");
        users1.setId(123L);
        users1.setLastName("Doe");
        users1.setPassword("iloveyou");
        users1.setPhoneNumber("4105551212");
        users1.setTransactionPin("Transaction Pin");
        users1.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users1.setUserStatus(UserStatus.ACTIVE);
        users1.setWallet(wallet);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUsers(users1);
        Optional<ConfirmationToken> ofResult = Optional.of(confirmationToken);
        when(confirmationTokenRepository.findByToken((String) any())).thenReturn(ofResult);
        Optional<ConfirmationToken> actualToken = confirmationTokenServiceImpl.getToken("ABC123");
        assertSame(ofResult, actualToken);
        assertTrue(actualToken.isPresent());
        verify(confirmationTokenRepository).findByToken((String) any());
    }


    @Test
    void testSetConfirmedAt() {
        when(confirmationTokenRepository.updateConfirmedAt((String) any(), (LocalDateTime) any())).thenReturn(1);
        assertEquals(1, confirmationTokenServiceImpl.setConfirmedAt("ABC123"));
        verify(confirmationTokenRepository).updateConfirmedAt((String) any(), (LocalDateTime) any());
    }
}

