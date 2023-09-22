package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.dto.WalletDto;
import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.models.Wallet;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {WalletServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class WalletServiceImplTest {
    @Mock
    private UsersRepository usersRepository;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletServiceImpl walletServiceImpl;


    @Test
    void viewWalletDetails() {
        WalletDto walletDto = new WalletDto();
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("jane.doe@example.org").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        Wallet wallet = new Wallet();
                wallet.setAcctNumber("1234567890");
                wallet.setBalance(10.0d);
                wallet.setBankName("Bank Name");
                wallet.setCreatedAt(null);
                wallet.setId(123L);
                wallet.setTransactionList(new ArrayList<>());
                wallet.setUpdatedAt(null);
                wallet.setUsers(new Users());

                walletDto.setAcctNumber(wallet.getAcctNumber());
                walletDto.setBalance(wallet.getBalance());
                walletDto.setBankName(wallet.getBankName());

        Users user = new Users();
                user.setBvn("Bvn");
                user.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
                user.setEmail("jane.doe@example.org");
                user.setFirstName("Jane");
                user.setId(10L);
                user.setLastName("Doe");
                user.setPassword("password");
                user.setPhoneNumber("4105551212");
                user.setTransactionPin("Transaction Pin");
                user.setUserStatus(UserStatus.ACTIVE);
                user.setWallet(wallet);

        when(usersRepository.findByEmail("jane.doe@example.org")).thenReturn(Optional.of(user));
        WalletDto toReturn = walletServiceImpl.viewWalletDetails();
        assertThat(Objects.equals(toReturn.getAcctNumber(), wallet.getAcctNumber()));
    }
}

