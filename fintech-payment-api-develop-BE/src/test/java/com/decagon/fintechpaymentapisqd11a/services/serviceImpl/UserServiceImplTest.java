package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.exceptions.EmailAlreadyTakenException;
import com.decagon.fintechpaymentapisqd11a.exceptions.UserNotFoundException;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.ConfirmationTokenRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import com.decagon.fintechpaymentapisqd11a.response.UserResponse;
import com.decagon.fintechpaymentapisqd11a.services.WalletService;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;
import com.decagon.fintechpaymentapisqd11a.util.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private Util util;

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private WalletService walletService;


    @Test
    void testRegisterUser() throws JSONException {
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
        Optional<Users> ofResult = Optional.of(users1);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(util.validatePassword((String) any(), (String) any())).thenReturn(true);
        assertThrows(EmailAlreadyTakenException.class, () -> userServiceImpl.registerUser(new RegistrationRequestDto("Jane",
                "Doe", "jane.doe@example.org", "4105551212", "Bvn", "iloveyou", "iloveyou", "Transaction Pin")));
        verify(usersRepository).findByEmail((String) any());
        verify(util).validatePassword((String) any(), (String) any());
    }


    @Test
    void testRegisterUser2() throws JSONException {
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
        Optional<Users> ofResult = Optional.of(users1);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(util.validatePassword((String) any(), (String) any()))
                .thenThrow(new EmailAlreadyTakenException("An error occurred"));
        assertThrows(EmailAlreadyTakenException.class, () -> userServiceImpl.registerUser(new RegistrationRequestDto("Jane",
                "Doe", "jane.doe@example.org", "4105551212", "Bvn", "iloveyou", "iloveyou", "Transaction Pin")));
        verify(usersRepository).findByEmail((String) any());
        verify(util).validatePassword((String) any(), (String) any());
    }


    @Test
    void testEnableUser() {
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
        Optional<Users> ofResult = Optional.of(users1);

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

        Wallet wallet3 = new Wallet();
        wallet3.setAcctNumber("42");
        wallet3.setBalance(10.0d);
        wallet3.setBankName("Bank Name");
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setTransactionList(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users3);

        Users users4 = new Users();
        users4.setBvn("Bvn");
        users4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setTransactionPin("Transaction Pin");
        users4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users4.setUserStatus(UserStatus.ACTIVE);
        users4.setWallet(wallet3);
        when(usersRepository.save((Users) any())).thenReturn(users4);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        userServiceImpl.enableUser("jane.doe@example.org");
        verify(usersRepository).save((Users) any());
        verify(usersRepository).findByEmail((String) any());
    }


    @Test
    void testEnableUser2() {
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
        Optional<Users> ofResult = Optional.of(users1);
        when(usersRepository.save((Users) any())).thenThrow(new EmailAlreadyTakenException("An error occurred"));
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertThrows(EmailAlreadyTakenException.class, () -> userServiceImpl.enableUser("jane.doe@example.org"));
        verify(usersRepository).save((Users) any());
        verify(usersRepository).findByEmail((String) any());
    }


    @Test
    void testSaveToken() {
        doNothing().when(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());

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
        userServiceImpl.saveToken("ABC123", users1);
        verify(confirmationTokenServiceImpl).saveConfirmationToken((ConfirmationToken) any());
    }

    @Test
    void testLoadUserByUsername() throws UserNotFoundException {
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
        Optional<Users> ofResult = Optional.of(users1);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userServiceImpl.loadUserByUsername("jane.doe@example.org");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("jane.doe@example.org", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(usersRepository).findByEmail((String) any());
    }


    @Test
    void testLoadUserByUsername2() throws UserNotFoundException {
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
        Users users1 = mock(Users.class);
        when(users1.getEmail()).thenReturn("jane.doe@example.org");
        when(users1.getPassword()).thenReturn("iloveyou");
        doNothing().when(users1).setCreatedAt((LocalDateTime) any());
        doNothing().when(users1).setId((java.lang.Long) any());
        doNothing().when(users1).setUpdatedAt((LocalDateTime) any());
        doNothing().when(users1).setBvn((String) any());
        doNothing().when(users1).setEmail((String) any());
        doNothing().when(users1).setFirstName((String) any());
        doNothing().when(users1).setLastName((String) any());
        doNothing().when(users1).setPassword((String) any());
        doNothing().when(users1).setPhoneNumber((String) any());
        doNothing().when(users1).setTransactionPin((String) any());
        doNothing().when(users1).setUserStatus((UserStatus) any());
        doNothing().when(users1).setWallet((Wallet) any());
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
        Optional<Users> ofResult = Optional.of(users1);
        when(usersRepository.findByEmail((String) any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = userServiceImpl.loadUserByUsername("jane.doe@example.org");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("jane.doe@example.org", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(usersRepository).findByEmail((String) any());
        verify(users1).getEmail();
        verify(users1).getPassword();
        verify(users1).setCreatedAt((LocalDateTime) any());
        verify(users1).setId((java.lang.Long) any());
        verify(users1).setUpdatedAt((LocalDateTime) any());
        verify(users1).setBvn((String) any());
        verify(users1).setEmail((String) any());
        verify(users1).setFirstName((String) any());
        verify(users1).setLastName((String) any());
        verify(users1).setPassword((String) any());
        verify(users1).setPhoneNumber((String) any());
        verify(users1).setTransactionPin((String) any());
        verify(users1).setUserStatus((UserStatus) any());
        verify(users1).setWallet((Wallet) any());
    }


}

