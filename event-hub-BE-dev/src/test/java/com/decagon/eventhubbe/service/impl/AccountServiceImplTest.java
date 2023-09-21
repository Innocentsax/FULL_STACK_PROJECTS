package com.decagon.eventhubbe.service.impl;

import com.decagon.eventhubbe.domain.entities.Account;
import com.decagon.eventhubbe.domain.entities.AppUser;
import com.decagon.eventhubbe.domain.entities.Banks;
import com.decagon.eventhubbe.domain.repository.*;
import com.decagon.eventhubbe.dto.request.RequestAccountDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.decagon.eventhubbe.utils.PaymentUtils.getSecretKey;

@SpringBootTest
class AccountServiceImplTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BankRepository bankRepository;

    @Mock
    private EventTicketRepository eventTicketRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AppUserServiceImpl appUserService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void getBankApiCodeDetails() {

    }


//    @Test
//    void saveAccount() {
//
//        RequestAccountDTO requestAccountDTO = new RequestAccountDTO();
//        requestAccountDTO.setAccountName("TEMPLE JACK WILLIAM CHIORLU");
//        requestAccountDTO.setBankName("Access Bank");
//        requestAccountDTO.setAccountNumber("1234567890");
//
//        String userEmail = "chiorlujack@gmail.com";
//        session(userEmail);
//        AppUser appUser = new AppUser();
//        appUser.setEmail(userEmail);
//
//        Banks banks = new Banks();
//        banks.setBankCode("044");
//        HttpHeaders headers = Mockito.mock(HttpHeaders.class);
//        headers.setBearerAuth(getSecretKey());
//
//        // Set the content type on the mock headers
//        Mockito.doNothing().when(headers).setContentType(MediaType.APPLICATION_JSON);
//        Mockito.doNothing().when(headers).setBearerAuth(getSecretKey());
//
//        // Mock the subAccount method call
//
//
//        Mockito.when(appUserRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(appUser));
//        Mockito.when(bankRepository.findAllByCode(Mockito.anyString())).thenReturn(banks);
////        Mockito.when(subAccount(Mockito.eq(headers), Mockito.any()))
////                .thenReturn(new SubAccountResponse("ACCT_cztzxugcr6tsd0g"));
//
//
//        RequestAccountDTO result = accountService.saveAccount(requestAccountDTO);
//
//        Mockito.verify(appUserRepository, Mockito.times(1)).findByEmail(userEmail);
//        Mockito.verify(bankRepository, Mockito.times(1)).findAllByCode(requestAccountDTO.getBankName());
//        Mockito.verify(accountRepository, Mockito.times(1)).save(Mockito.any(Account.class));
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(requestAccountDTO.getAccountName(), result.getAccountName());
//        Assertions.assertEquals(requestAccountDTO.getBankName(), result.getBankName());
//        Assertions.assertEquals(requestAccountDTO.getAccountNumber(), result.getAccountNumber());
////        Assertions.assertEquals("subaccount-code", result.getSubaccountCode());
//
//        SecurityContextHolder.clearContext();
//    }


    @Test
    void getBankCodeAndSend() {
    }

    @Test
    void subAccount() {
    }

    @Test
    void updateAccount() {
    }
    public void session(String userEmail) {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(Mockito.mock(Authentication.class));
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(userEmail);
        SecurityContextHolder.setContext(securityContext);
    }
}