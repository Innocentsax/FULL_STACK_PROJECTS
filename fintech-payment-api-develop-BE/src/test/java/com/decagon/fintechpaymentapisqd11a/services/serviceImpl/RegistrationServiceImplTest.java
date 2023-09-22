package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fintechpaymentapisqd11a.dto.MailServiceDto;
import com.decagon.fintechpaymentapisqd11a.dto.RegistrationRequestDto;
import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.exceptions.ConfirmationTokenException;
import com.decagon.fintechpaymentapisqd11a.exceptions.EmailNotValidException;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.token.ConfirmationToken;
import com.decagon.fintechpaymentapisqd11a.validations.EmailValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RegistrationServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RegistrationServiceImplTest {
    @MockBean
    private ConfirmationTokenServiceImpl confirmationTokenServiceImpl;

    @MockBean
    private EmailValidator emailValidator;

    @MockBean
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private RegistrationServiceImpl registrationServiceImpl;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;


    @Test
    void testCreateUser() throws JSONException, MailException {
        when(userServiceImpl.registerUser((RegistrationRequestDto) any())).thenReturn("Register User");
        when(emailValidator.test((String) any())).thenReturn(true);
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());
        assertEquals("Please check your email to verify your account",
                registrationServiceImpl.createUser(new RegistrationRequestDto("Jane", "Doe", "jane.doe@example.org",
                        "4105551212", "Bvn", "iloveyou", "iloveyou", "Transaction Pin")));
        verify(userServiceImpl).registerUser((RegistrationRequestDto) any());
        verify(emailValidator).test((String) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }


    @Test
    void testCreateUser2() throws JSONException, MailException {
        when(userServiceImpl.registerUser((RegistrationRequestDto) any())).thenReturn("Register User");
        when(emailValidator.test((String) any())).thenReturn(true);
        doThrow(new EmailNotValidException("An error occurred")).when(mailServiceImpl)
                .sendNotification((MailServiceDto) any());
        assertThrows(EmailNotValidException.class,
                () -> registrationServiceImpl.createUser(new RegistrationRequestDto("Jane", "Doe", "jane.doe@example.org",
                        "4105551212", "Bvn", "iloveyou", "iloveyou", "Transaction Pin")));
        verify(userServiceImpl).registerUser((RegistrationRequestDto) any());
        verify(emailValidator).test((String) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }


    @Test
    void testCreateUser3() throws JSONException, MailException {
        when(userServiceImpl.registerUser((RegistrationRequestDto) any())).thenReturn("Register User");
        when(emailValidator.test((String) any())).thenReturn(false);
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());
        assertThrows(EmailNotValidException.class,
                () -> registrationServiceImpl.createUser(new RegistrationRequestDto("Jane", "Doe", "jane.doe@example.org",
                        "4105551212", "Bvn", "iloveyou", "iloveyou", "Transaction Pin")));
        verify(emailValidator).test((String) any());
    }

    @Test
    void testConfirmToken() {
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
        when(confirmationTokenServiceImpl.getToken((String) any())).thenReturn(ofResult);
        assertThrows(ConfirmationTokenException.class, () -> registrationServiceImpl.confirmToken("ABC123"));
        verify(confirmationTokenServiceImpl).getToken((String) any());
    }


    @Test
    void testConfirmToken2() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());

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

        Users users4 = new Users();
        users4.setBvn("Bvn");
        users4.setCreatedAt(null);
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setTransactionPin("Transaction Pin");
        users4.setUpdatedAt(null);
        users4.setUserStatus(UserStatus.ACTIVE);
        users4.setWallet(new Wallet());

        Wallet wallet3 = new Wallet();
        wallet3.setAcctNumber("42");
        wallet3.setBalance(10.0d);
        wallet3.setBankName("Bank Name");
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setTransactionList(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users4);

        Users users5 = new Users();
        users5.setBvn("Bvn");
        users5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setId(123L);
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhoneNumber("4105551212");
        users5.setTransactionPin("Transaction Pin");
        users5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setUserStatus(UserStatus.ACTIVE);
        users5.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAcctNumber("42");
        wallet4.setBalance(10.0d);
        wallet4.setBankName("Bank Name");
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setTransactionList(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users5);

        Users users6 = new Users();
        users6.setBvn("Bvn");
        users6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setEmail("jane.doe@example.org");
        users6.setFirstName("Jane");
        users6.setId(123L);
        users6.setLastName("Doe");
        users6.setPassword("iloveyou");
        users6.setPhoneNumber("4105551212");
        users6.setTransactionPin("Transaction Pin");
        users6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setUserStatus(UserStatus.ACTIVE);
        users6.setWallet(wallet4);
        ConfirmationToken confirmationToken = mock(ConfirmationToken.class);
        when(confirmationToken.getUsers()).thenReturn(users6);
        when(confirmationToken.getConfirmedAt()).thenReturn(null);
        when(confirmationToken.getExpiresAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(confirmationToken).setConfirmedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setCreatedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setExpiresAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setId((java.lang.Long) any());
        doNothing().when(confirmationToken).setToken((String) any());
        doNothing().when(confirmationToken).setUsers((Users) any());
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUsers(users3);
        Optional<ConfirmationToken> ofResult1 = Optional.of(confirmationToken);
        when(confirmationTokenServiceImpl.getToken((String) any())).thenReturn(ofResult1);
        assertEquals("Verification token expired. Check email for a new verification token",
                registrationServiceImpl.confirmToken("ABC123"));
        verify(userServiceImpl).saveToken((String) any(), (Users) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
        verify(usersRepository).findByEmail((String) any());
        verify(confirmationTokenServiceImpl).getToken((String) any());
        verify(confirmationToken).getUsers();
        verify(confirmationToken).getConfirmedAt();
        verify(confirmationToken).getExpiresAt();
        verify(confirmationToken).setConfirmedAt((LocalDateTime) any());
        verify(confirmationToken).setCreatedAt((LocalDateTime) any());
        verify(confirmationToken).setExpiresAt((LocalDateTime) any());
        verify(confirmationToken).setId((java.lang.Long) any());
        verify(confirmationToken).setToken((String) any());
        verify(confirmationToken).setUsers((Users) any());
    }


    @Test
    void testConfirmToken3() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doThrow(new EmailNotValidException("An error occurred")).when(mailServiceImpl)
                .sendNotification((MailServiceDto) any());

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

        Users users4 = new Users();
        users4.setBvn("Bvn");
        users4.setCreatedAt(null);
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setTransactionPin("Transaction Pin");
        users4.setUpdatedAt(null);
        users4.setUserStatus(UserStatus.ACTIVE);
        users4.setWallet(new Wallet());

        Wallet wallet3 = new Wallet();
        wallet3.setAcctNumber("42");
        wallet3.setBalance(10.0d);
        wallet3.setBankName("Bank Name");
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setTransactionList(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users4);

        Users users5 = new Users();
        users5.setBvn("Bvn");
        users5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setId(123L);
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhoneNumber("4105551212");
        users5.setTransactionPin("Transaction Pin");
        users5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setUserStatus(UserStatus.ACTIVE);
        users5.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAcctNumber("42");
        wallet4.setBalance(10.0d);
        wallet4.setBankName("Bank Name");
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setTransactionList(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users5);

        Users users6 = new Users();
        users6.setBvn("Bvn");
        users6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setEmail("jane.doe@example.org");
        users6.setFirstName("Jane");
        users6.setId(123L);
        users6.setLastName("Doe");
        users6.setPassword("iloveyou");
        users6.setPhoneNumber("4105551212");
        users6.setTransactionPin("Transaction Pin");
        users6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setUserStatus(UserStatus.ACTIVE);
        users6.setWallet(wallet4);
        ConfirmationToken confirmationToken = mock(ConfirmationToken.class);
        when(confirmationToken.getUsers()).thenReturn(users6);
        when(confirmationToken.getConfirmedAt()).thenReturn(null);
        when(confirmationToken.getExpiresAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(confirmationToken).setConfirmedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setCreatedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setExpiresAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setId((java.lang.Long) any());
        doNothing().when(confirmationToken).setToken((String) any());
        doNothing().when(confirmationToken).setUsers((Users) any());
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUsers(users3);
        Optional<ConfirmationToken> ofResult1 = Optional.of(confirmationToken);
        when(confirmationTokenServiceImpl.getToken((String) any())).thenReturn(ofResult1);
        assertThrows(EmailNotValidException.class, () -> registrationServiceImpl.confirmToken("ABC123"));
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
        verify(usersRepository).findByEmail((String) any());
        verify(confirmationTokenServiceImpl).getToken((String) any());
        verify(confirmationToken).getUsers();
        verify(confirmationToken).getConfirmedAt();
        verify(confirmationToken).getExpiresAt();
        verify(confirmationToken).setConfirmedAt((LocalDateTime) any());
        verify(confirmationToken).setCreatedAt((LocalDateTime) any());
        verify(confirmationToken).setExpiresAt((LocalDateTime) any());
        verify(confirmationToken).setId((java.lang.Long) any());
        verify(confirmationToken).setToken((String) any());
        verify(confirmationToken).setUsers((Users) any());
    }


    @Test
    void testConfirmToken4() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());

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
        when(users1.getFirstName()).thenReturn("Jane");
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

        Users users4 = new Users();
        users4.setBvn("Bvn");
        users4.setCreatedAt(null);
        users4.setEmail("jane.doe@example.org");
        users4.setFirstName("Jane");
        users4.setId(123L);
        users4.setLastName("Doe");
        users4.setPassword("iloveyou");
        users4.setPhoneNumber("4105551212");
        users4.setTransactionPin("Transaction Pin");
        users4.setUpdatedAt(null);
        users4.setUserStatus(UserStatus.ACTIVE);
        users4.setWallet(new Wallet());

        Wallet wallet3 = new Wallet();
        wallet3.setAcctNumber("42");
        wallet3.setBalance(10.0d);
        wallet3.setBankName("Bank Name");
        wallet3.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setId(123L);
        wallet3.setTransactionList(new ArrayList<>());
        wallet3.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet3.setUsers(users4);

        Users users5 = new Users();
        users5.setBvn("Bvn");
        users5.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setEmail("jane.doe@example.org");
        users5.setFirstName("Jane");
        users5.setId(123L);
        users5.setLastName("Doe");
        users5.setPassword("iloveyou");
        users5.setPhoneNumber("4105551212");
        users5.setTransactionPin("Transaction Pin");
        users5.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users5.setUserStatus(UserStatus.ACTIVE);
        users5.setWallet(wallet3);

        Wallet wallet4 = new Wallet();
        wallet4.setAcctNumber("42");
        wallet4.setBalance(10.0d);
        wallet4.setBankName("Bank Name");
        wallet4.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setId(123L);
        wallet4.setTransactionList(new ArrayList<>());
        wallet4.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        wallet4.setUsers(users5);

        Users users6 = new Users();
        users6.setBvn("Bvn");
        users6.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setEmail("jane.doe@example.org");
        users6.setFirstName("Jane");
        users6.setId(123L);
        users6.setLastName("Doe");
        users6.setPassword("iloveyou");
        users6.setPhoneNumber("4105551212");
        users6.setTransactionPin("Transaction Pin");
        users6.setUpdatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        users6.setUserStatus(UserStatus.ACTIVE);
        users6.setWallet(wallet4);
        ConfirmationToken confirmationToken = mock(ConfirmationToken.class);
        when(confirmationToken.getUsers()).thenReturn(users6);
        when(confirmationToken.getConfirmedAt()).thenReturn(null);
        when(confirmationToken.getExpiresAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        doNothing().when(confirmationToken).setConfirmedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setCreatedAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setExpiresAt((LocalDateTime) any());
        doNothing().when(confirmationToken).setId((java.lang.Long) any());
        doNothing().when(confirmationToken).setToken((String) any());
        doNothing().when(confirmationToken).setUsers((Users) any());
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUsers(users3);
        Optional<ConfirmationToken> ofResult1 = Optional.of(confirmationToken);
        when(confirmationTokenServiceImpl.getToken((String) any())).thenReturn(ofResult1);
        assertEquals("Verification token expired. Check email for a new verification token",
                registrationServiceImpl.confirmToken("ABC123"));
        verify(userServiceImpl).saveToken((String) any(), (Users) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
        verify(usersRepository).findByEmail((String) any());
        verify(users1).getEmail();
        verify(users1).getFirstName();
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
        verify(confirmationTokenServiceImpl).getToken((String) any());
        verify(confirmationToken).getUsers();
        verify(confirmationToken).getConfirmedAt();
        verify(confirmationToken).getExpiresAt();
        verify(confirmationToken).setConfirmedAt((LocalDateTime) any());
        verify(confirmationToken).setCreatedAt((LocalDateTime) any());
        verify(confirmationToken).setExpiresAt((LocalDateTime) any());
        verify(confirmationToken).setId((java.lang.Long) any());
        verify(confirmationToken).setToken((String) any());
        verify(confirmationToken).setUsers((Users) any());
    }


    @Test
    void testResendEmail() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());

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
        registrationServiceImpl.resendEmail(users1);
        verify(userServiceImpl).saveToken((String) any(), (Users) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }


    @Test
    void testResendEmail2() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doThrow(new EmailNotValidException("An error occurred")).when(mailServiceImpl)
                .sendNotification((MailServiceDto) any());

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
        assertThrows(EmailNotValidException.class, () -> registrationServiceImpl.resendEmail(users1));
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }

    @Test
    void testResendEmail3() throws MailException {
        doNothing().when(userServiceImpl).saveToken((String) any(), (Users) any());
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());

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
        when(users1.getFirstName()).thenReturn("Jane");
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
        registrationServiceImpl.resendEmail(users1);
        verify(userServiceImpl).saveToken((String) any(), (Users) any());
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
        verify(users1).getEmail();
        verify(users1).getFirstName();
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

    @Test
    void testSendMail() throws MailException {
        doNothing().when(mailServiceImpl).sendNotification((MailServiceDto) any());
        registrationServiceImpl.sendMail("Name", "jane.doe@example.org", "Link");
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }


    @Test
    void testSendMail2() throws MailException {
        doThrow(new EmailNotValidException("An error occurred")).when(mailServiceImpl)
                .sendNotification((MailServiceDto) any());
        assertThrows(EmailNotValidException.class,
                () -> registrationServiceImpl.sendMail("Name", "jane.doe@example.org", "Link"));
        verify(mailServiceImpl).sendNotification((MailServiceDto) any());
    }
}

