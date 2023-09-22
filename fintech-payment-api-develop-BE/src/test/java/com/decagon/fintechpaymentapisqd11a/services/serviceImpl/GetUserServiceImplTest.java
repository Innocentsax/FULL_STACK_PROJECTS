package com.decagon.fintechpaymentapisqd11a.services.serviceImpl;

import com.decagon.fintechpaymentapisqd11a.enums.UserStatus;
import com.decagon.fintechpaymentapisqd11a.models.Users;
import com.decagon.fintechpaymentapisqd11a.repositories.UsersRepository;
import com.decagon.fintechpaymentapisqd11a.response.UserResponse;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetUserServiceImplTest {

    @Autowired
    private   UserServiceImpl userService = mock(UserServiceImpl.class);

   @Autowired
    UsersRepository usersRepository = mock(UsersRepository.class);


    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUser() {

            Users users1 = new Users();
            users1.setBvn("26890026517");
            users1.setEmail("presh@gmail.com");
            users1.setFirstName("Peace");
            users1.setLastName("Okoye");
            users1.setPassword("1234567");
            users1.setPhoneNumber("08124589067");

            Authentication authentication = mock(Authentication.class);

            SecurityContext securityContext = mock(SecurityContext.class);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);
            when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(users1);


            when(usersRepository.findByEmail("jane.doe@example.org")).thenReturn(Optional.of(users1));

            UserResponse userResponse = new UserResponse();
            userResponse.setEmail("PeaceOnyinye@gmail.com");
            userResponse.setFirstName("Onyinye");
            userResponse.setLastName("Peace");
            users1.setBvn("26890066517");
            userResponse.setPhoneNumber("07022299900");

            assertNotEquals(new UserResponse(),userService.getUser());


        }


    }
