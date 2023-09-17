package com.wakacast.services.service_impl;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.pages_criteria.ApplicantPage;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.enums.Gender;
import com.wakacast.enums.UserType;
import com.wakacast.models.*;
import com.wakacast.repositories.CastCallRepository;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.withUsername;

@ExtendWith(MockitoExtension.class)
class CastCallServiceImplTest {
    @Mock
    private ModelMapper mapper;
    @Mock
    private CastCallRepository castCallRepository;
    @InjectMocks
    private CastCallServiceImpl castCallService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("ony@gmail.com");
        user.setFirstName("Ony");

        CastCallDto castCallDto = new CastCallDto();
        castCallDto.setProjectName("Wakacast");
        castCallDto.setProjectType("TV Commercial");
        castCallDto.setState("Lagos");
        castCallDto.setCity("Lagos");
        castCallDto.setCountry("Nigeria");
        castCallDto.setMinAge(18);
        castCallDto.setMaxAge(35);
        castCallDto.setPostExpiryDate(LocalDate.of(2022, 1, 26));
        castCallDto.setProjectDescription("An advertorial for TV series");
        castCallDto.setOtherRequirements("Casts must be at least 5ft 6\" and good looking");
        castCallDto.setLanguages("English");
        castCallDto.setTalentSkill("Musician");

    }

    @Test
    void postCastCall() {
        User user = new User();
        CastCall castCall = new CastCall();
        CastCallDto castCallDto = new CastCallDto();
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("ony@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));

        user.setUserType(UserType.SUBSCRIBED);

        UserPersona userPersona = new UserPersona();
        userPersona.setPersona("Producer");
        Set<UserPersona> personas = new HashSet<>();
        personas.add(userPersona);
        user.setUserPersonas(personas);
        user.setId(1L);
        List<CastCall> castCalls = new ArrayList<>();
        when(castCallRepository.findCastCallByPublisherId(1L)).thenReturn(castCalls);
        when(mapper.map(castCallDto, CastCall.class)).thenReturn(castCall);
        String value = castCallService.postCastCall(castCallDto);
        assertThat(value).isNotNull();
    }

    @Test
    void editCastCall() {
        CastCallDto castCallDto = new CastCallDto();
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("ony@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        User user = new User();
        user.setUserType(UserType.UNSUBSCRIBED);
        user.setId(1L);
        user.setEmail("ony@gmail.com");
        user.setPassword("password");
        CastCall castCall = new CastCall();
        castCall.setId(1L);
        castCall.setPostExpiryDate(LocalDate.now().plusDays(1));
        castCall.setPublisher(user);
        List<CastCall> castCalls = new ArrayList<>();
        castCalls.add(castCall);
        when(castCallRepository.findCastCallByPublisherEmailAndId(user.getEmail(), 1L)).thenReturn(castCall);
        when(castCallRepository.findCastCallByPublisherId(user.getId())).thenReturn(castCalls);
        String value = castCallService.editCastCall(castCallDto, 1L);
        assertThat(value).isNotNull();
    }

    @Test
    void deleteCastCall() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("ony@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        String value = castCallService.deleteCastCall(1L);
        assertThat(value).isEqualTo("cast call deleted successfully");
    }

    @Test
    void getNonExpiredCastCall() {
    CastCallPage castCallPage = new CastCallPage();

    CastCall call1 = new CastCall();
    CastCall call2 = new CastCall();
    CastCall call3 = new CastCall();
        call1.setPostExpiryDate(LocalDate.now().plusDays(1));
        call2.setPostExpiryDate(LocalDate.of(2021,1,26));
        call3.setPostExpiryDate(LocalDate.now().plusDays(3));
    List<CastCall> page = Arrays.asList(call1, call2, call3);

    Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
    Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
    when(castCallRepository.findCastCallsByPostExpiryDateBefore(pageable)).thenReturn(new PageImpl<>(page));
    Page<CastCall> returnedPage = castCallService.getAllActiveCastCalls(castCallPage);
    assertThat(returnedPage).isNotNull();
    assertThat(returnedPage.getTotalElements()).isEqualTo(page.size());
}

    @Test
    void applyForCastCall() throws IOException {

        User user1 = new User();
        CastCall newCastCall = new CastCall();
        User producer1 = new User();
        Set<User> applicants = new HashSet<>();
        user1.setEmail("davidbaba2013@gmail.com");
        user1.setFirstName("User1");
        user1.setSurname("One");
        user1.setTitle("Mr");
        user1.setDisplayName("User1");
        user1.setPassword("User1@2021");
        user1.setGender(Gender.MALE);
        user1.setState("Active");
        user1.setUserType(UserType.SUBSCRIBED);
        user1.setAccountVerified(true);
        user1.setState("Edo");
        user1.setCountry("Nigeria");
        user1.setCity("Benin");
        user1.setAccountVerified(true);
        user1.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Talent"))));
        user1.setRoles(new HashSet<>(Set.of(new Role("Actor"))));
        user1.setGenres(new HashSet<>(Set.of(new Genre("Movie"))));


        newCastCall.setId(1L);
        newCastCall.setGender(Gender.MALE);
        newCastCall.setLanguages("Yoruba");
        newCastCall.setState("Lagos");
        newCastCall.setCity("Ajegunle");
        newCastCall.setCountry("Nigeria");
        newCastCall.setPostExpiryDate(LocalDate.of(2022, 04, 10));//Yeear/Month/Day
        newCastCall.setProjectDescription("To find a role that can act and sing at the same time");
        newCastCall.setProjectName("Singa pool");
        newCastCall.setProjectType("Audit");
        newCastCall.setTalentSkill("Actor");
        newCastCall.setPublisher(producer1);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("davidbaba2013@gmail.com").password("User1@2021").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(userRepository.findUserByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        when(castCallRepository.findCastCallById(newCastCall.getId())).thenReturn(newCastCall);
        String returnedValue = castCallService.applyForCastCall(1L);
        String msg = "Your application was received successfully";
        assertThat(returnedValue).isNotNull().isEqualTo(msg);

    }

    @Test
    void getAllCastCallsByAProducer() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("ony@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        CastCallPage castCallPage = new CastCallPage();
        CastCall castCall = new CastCall();
        User user1 = new User();
        castCall.setPublisher(user1);
        List<CastCall> castCalls = List.of(castCall);
        Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
        when(castCallRepository.findCastCallsByPublisherEmail("ony@gmail.com", pageable)).thenReturn(new PageImpl<>(castCalls));
        Page<CastCallDto> returnedList = castCallService.getAllCastCallsByAProducer(castCallPage);
        assertThat(returnedList).isNotNull();
        assertThat(returnedList.getTotalElements()).isEqualTo(castCalls.size());
    }

    @Test
    void getCastCallsById() {
        Long castCallId = 1L;
        CastCall castCall = new CastCall();
        CastCallDto castCallDto = new CastCallDto();
        when(castCallRepository.findCastCallById(castCallId)).thenReturn(castCall);
        when(mapper.map(castCall, CastCallDto.class)).thenReturn(castCallDto);
        CastCallDto returnedCastCall = castCallService.getCastCallById(castCallId);
        assertThat(returnedCastCall).isNotNull();

    }

    @Test
    void getCastCallApplicant() {
        User user = new User();
        user.setId(1L);
        user.setActive(true);
        user.setAccountVerified(true);
        user.setRoles(new HashSet<>(Set.of(new Role("DANCER"))));
        user.setUserType(UserType.SUBSCRIBED);
        user.setUserPersonas(new HashSet<>(Set.of(new UserPersona("TALENT"))));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setActive(true);
        userResponseDto.setActive(true);
        userResponseDto.setAccountVerified(true);
        userResponseDto.setRoles(new HashSet<>(Set.of(new Role("DANCER"))));
        userResponseDto.setUserType(UserType.SUBSCRIBED);
        userResponseDto.setUserPersonas(new HashSet<>(Set.of(new UserPersona("TALENT"))));

        CastCall castCall = new CastCall();
        castCall.setId(1L);
        castCall.setApplicants(new HashSet<>(Set.of(user)));
        ApplicantPage page = new ApplicantPage();

        when(castCallRepository.findCastCallById(castCall.getId())).thenReturn(castCall);
        when(mapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);
        Page<UserResponseDto> responseDtoPage = castCallService.getCastCallApplicants(castCall.getId(), page);
        assertThat(responseDtoPage).isNotNull();
        assertThat(responseDtoPage.getTotalElements()).isEqualTo(1);
    }
}