package com.wakacast.services.service_impl;

import com.wakacast.dto.NotificationMessageDto;
import com.wakacast.dto.pages_criteria.NotificationPage;
import com.wakacast.dto.response_dtos.NotificationMessageResponseDto;
import com.wakacast.enums.Gender;
import com.wakacast.enums.UserType;
import com.wakacast.models.*;
import com.wakacast.repositories.NotificationMessageRepository;
import com.wakacast.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.withUsername;

@ExtendWith(MockitoExtension.class)
class NotificationMessageServiceImplTest {
    User sender;
    User receiver;
    NotificationMessageDto notificationMessageDto;
    NotificationMessage notificationMessage;
    NotificationPage notificationPage;
    Page<NotificationMessage> page;
    Pageable pageable;

    @Mock
    private ModelMapper mapper;
    @Mock
    private NotificationMessageRepository notificationMessageRepository;
    @InjectMocks
    private NotificationMessageServiceImpl notificationMessageService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        sender = new User();
        receiver = new User();

        sender.setEmail("davidbaba2013@gmail.com");
        sender.setFirstName("User1");
        sender.setSurname("One");
        sender.setTitle("Mr");
        sender.setDisplayName("User1");
        sender.setPassword("User1@2021");
        sender.setGender(Gender.MALE);
        sender.setState("Active");
        sender.setUserType(UserType.SUBSCRIBED);
        sender.setAccountVerified(true);
        sender.setState("Edo");
        sender.setCountry("Nigeria");
        sender.setCity("Benin");
        sender.setAccountVerified(true);
        sender.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Talent"))));
        sender.setRoles(new HashSet<>(Set.of(new Role("Actor"))));
        sender.setGenres(new HashSet<>(Set.of(new Genre("Movie"))));

        receiver.setEmail("receiver2022@gmail.com");
        receiver.setFirstName("receiver1");
        receiver.setSurname("One");
        receiver.setTitle("Mr");
        receiver.setDisplayName("receiver1");
        receiver.setPassword("Receiver1@2021");
        receiver.setGender(Gender.MALE);
        receiver.setState("Active");
        receiver.setUserType(UserType.SUBSCRIBED);
        receiver.setAccountVerified(true);
        receiver.setState("Edo");
        receiver.setCountry("Nigeria");
        receiver.setCity("Benin");
        receiver.setAccountVerified(true);
        receiver.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Talent"))));
        receiver.setRoles(new HashSet<>(Set.of(new Role("Actor"))));
        receiver.setGenres(new HashSet<>(Set.of(new Genre("Movie"))));
        notificationMessageDto = new NotificationMessageDto();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("davidbaba2013@gmail.com").password("User1@2021").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);


        notificationMessageDto.setContent("Hello there.....");
        notificationMessageDto.setReceiverEmail(receiver.getEmail());
        notificationMessage = new NotificationMessage();
        notificationMessage.setContent(notificationMessageDto.getContent());
        notificationMessage.setSenderEmail(sender.getEmail());
        notificationMessage.setReceiverEmail(receiver.getEmail());


        notificationPage = new NotificationPage();


        Sort sort = Sort.by(notificationPage.getSortDirection(), notificationPage.getSortBy());
        pageable = PageRequest.of(notificationPage.getPageNumber(), notificationPage.getPageSize(), sort);
        page = new PageImpl(List.of(), pageable, 0);
    }


    @Test
    void sendMessageTo() throws IOException {

        when(userRepository.findUserByEmail(sender.getEmail())).thenReturn(Optional.of(sender));
        when(userRepository.findUserByEmail(receiver.getEmail())).thenReturn(Optional.of(receiver));
        when(notificationMessageRepository.save(notificationMessage)).thenReturn(notificationMessage);
        when(mapper.map(notificationMessageDto, NotificationMessage.class)).thenReturn(notificationMessage);
        String msg = "Your notification was sent successfully.";
        String valueCheck = notificationMessageService.sendMessageTo(notificationMessageDto);
        assertThat(valueCheck).isNotNull().isEqualTo(msg);
    }

    @Test
    void getAllSentMessages() {

        when(notificationMessageRepository.findNotificationMessageBySenderEmail(sender.getEmail(), pageable)).thenReturn(page);
        Page<NotificationMessageResponseDto> returnedPage = notificationMessageService.getAllSentMessages(notificationPage);
        Page<NotificationMessageResponseDto> response = page.map(message -> mapper.map(message, NotificationMessageResponseDto.class));
        assertThat(page).isNotNull();
        assertThat(returnedPage).isEqualTo(response);
    }

    @Test
    void getAllReceivedMessages() {

        when(notificationMessageRepository.findNotificationMessageByReceiverEmail(sender.getEmail(), pageable)).thenReturn(page);
        Page<NotificationMessageResponseDto> returned = notificationMessageService.getAllReceivedMessages(notificationPage);
        Page<NotificationMessageResponseDto> res = page.map(message -> mapper.map(message, NotificationMessageResponseDto.class));
        assertThat(returned).isNotNull().isEqualTo(res);
    }
}