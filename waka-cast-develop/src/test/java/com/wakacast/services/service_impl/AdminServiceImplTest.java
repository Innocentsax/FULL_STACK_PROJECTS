package com.wakacast.services.service_impl;


import com.wakacast.dto.*;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.CastCallReportPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.enums.Gender;
import com.wakacast.enums.UserType;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.UnauthorizedException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.*;
import com.wakacast.repositories.*;
import com.wakacast.repositories.criteri_class.UserSearchCriteriaRepository;
import com.wakacast.requests.EmailRequest;
import com.wakacast.responses.JwtResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CastCallRepository castCallRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserSearchCriteriaRepository userSearchCriteriaRepository;
    @Mock
    private LanguageRepository languageRepository;
    @InjectMocks
    private AdminServiceImpl adminService;
    @Mock
    private ReportCastCallRepository reportCastCallRepository;
    @Mock
    private ReasonForReportingCastCallRepository reasonForReportingCastCallRepository;
    @Mock
    private UserServicesImpl userServices;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EquipmentTypeRepository equipmentTypeRepository;


    User user = new User();
    CastCall castCall = new CastCall();

    @BeforeEach
    void setUp() {
        user.setEmail("admin@gmail.com");
        user.setFirstName("Admin");

        castCall.setId(2L);
        castCall.setProjectName("Decagon");
        castCall.setProjectType("Recruiting Decadevs");
        castCall.setState("Lagos");
        castCall.setCity("High land");
        castCall.setCountry("Nigeria");
        castCall.setMinAge(15);
        castCall.setMaxAge(40);
        castCall.setPostExpiryDate(LocalDate.of(2022, 4, 26));
        castCall.setProjectDescription("An advertorial for intake of new decadevs");
        castCall.setOtherRequirements("Applications are welcomed from good candidates");
        castCall.setLanguages("English");
        castCall.setTalentSkill("Software Engineer");
        castCall.setReportedCount(0);
        castCall.setReportedEnough(false);
        castCall.setPublisher(user);
    }

    @Test
    void getAllUsers() {
        UserPage userPage = new UserPage();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        List<User> page = Arrays.asList(user1, user2, user3);

        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        Pageable pageable = PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(page));
        Page<UserDto> returnedPage = adminService.getAllUsers(userPage);
        assertThat(returnedPage).isNotNull();
        assertThat(returnedPage.getTotalElements()).isEqualTo(page.size());
    }

    @Test
    void generateLoginToken() throws IOException {
        User user = new User();
        user.setEmail("og@gmail.com");
        user.setUserType(UserType.ADMIN);

        when(userRepository.findUserByEmail("og@gmail.com")).thenReturn(Optional.of(user));
        ResponseEntity<JwtResponse> returnedValue = adminService.generateLoginToken(user.getEmail());
        assertThat(Objects.requireNonNull(returnedValue.getBody()).getToken())
                .isNotNull()
                .isEqualTo("Check your email for login token");
    }

    @Test
    void givenUnAuthorizedLogin_shouldThrow_UnAuthorizedException() {
        User user = new User();
        user.setEmail("og@gmail.com");
        user.setUserType(UserType.SUBSCRIBED);

        when(userRepository.findUserByEmail("og@gmail.com")).thenReturn(Optional.of(user));

        assertThrows(UnauthorizedException.class, ()-> adminService.generateLoginToken(user.getEmail()));
    }

    @Test
    void givenInvalidEmail_shouldThrow_UserWithEmailNotFoundException() {
        when(userRepository.findUserByEmail("og@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserWithEmailNotFound.class, ()-> adminService.generateLoginToken("og@gmail.com"));
    }

    @Test
    void confirmLoginToken() {
        User user = new User();
        user.setEmail("og@gmail.com");
        user.setUserType(UserType.ADMIN);
        user.setLoginToken("token");

        when(userRepository.findUserByLoginToken("token")).thenReturn(Optional.of(user));
        String returnedEmail = adminService.confirmLoginToken("token");
        assertThat(returnedEmail).isNotNull().isEqualTo(user.getEmail());
    }

    @Test
    void addNewGenre() {
        Genre genre = new Genre("TEST_GENRE");
        genre.setId(1L);
        genre.setCreateDate(LocalDateTime.now());
        GenreDto genreDto = new GenreDto("Test_Genre");
        when(mapper.map(genreDto, Genre.class)).thenReturn(genre);
        String resp = adminService.addGenre(genreDto);
        assertThat(resp).isNotNull().isEqualTo("New genre added successfully");

        when(genreRepository.findGenreByGenreTitle(genreDto.getGenreTitle().toUpperCase())).thenReturn(Optional.of(genre));
        assertThrows(BadRequestException.class, () -> adminService.addGenre(genreDto));
    }

    @Test
    void deleteCastCallAdmin() {
        when(castCallRepository.findCastCallById(castCall.getId())).thenReturn(castCall);
        String returnedValue = adminService.deleteCastCallAdmin(2L);
        String msg = "cast call deleted successfully";
        assertThat(returnedValue).isNotNull().isEqualTo(msg);
    }

@Test
void getAllCastCalls() {
    CastCallPage castCallPage = new CastCallPage();
    CastCall call1 = new CastCall();
    CastCall call2 = new CastCall();
    CastCall call3 = new CastCall();
    List<CastCall> page = Arrays.asList(call1,call2, call3);

    Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
    Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
    when(castCallRepository.findAll(pageable)).thenReturn(new PageImpl<>(page));
    Page<CastCallDtoAdmin> returnedPage = adminService.getAllCastCalls(castCallPage);
    assertThat(returnedPage).isNotNull();
    assertThat(returnedPage.getTotalElements()).isEqualTo(page.size());
}
    @Test
    void sendDailyEmails() throws IOException {
        User user = new User();
        List<User> users = List.of(user);

        DailyEmailDTO emailDTO = new DailyEmailDTO();

        when(userSearchCriteriaRepository.findUsers(emailDTO)).thenReturn(users);

        String resp = adminService.sendDailyEmails(emailDTO);
        assertThat(resp).isNotNull().isEqualTo("Email successfully sent");
    }

    @Test
    void givenNewLanguage_shouldAddNewLanguage() {
        LanguageDto languageDto = new LanguageDto();
        languageDto.setLanguage("French");
        String result = adminService.addLanguage(languageDto);
        assertThat(result).isNotNull().isEqualTo("New Language added successfully");
    }

    @Test
    void addReasonForReportingCastCall() {
        ReasonToSaveDto reasonDto = new ReasonToSaveDto();
        String mgs = "New reason saved successfully";
        reasonDto.setContent(mgs);
        when(reasonForReportingCastCallRepository.findReasonForReportingCastCallByContent(reasonDto.getContent()))
                .thenReturn(Optional.empty());
        String saveReason = adminService.addReasonForReportingCastCall(reasonDto);
        assertThat(saveReason).isNotNull().isEqualTo(mgs);
    }

    @Test
    void getAllCastCallReportedFormatted() {
        CastCallReportPage castCallReportPage = new CastCallReportPage();
        User user = new User();
        user.setFirstName("first name");
        user.setSurname("surname");
        user.setMiddleName("Middle name");
        CastCall castCall = new CastCall();
        castCall.setProjectName("Project Name");
        castCall.setProjectType("Project type");

        ReportedCastCall call1 = new ReportedCastCall();
        call1.setCastCallReported(castCall);
        call1.setCastCallReporter(user);
        ReportedCastCall call2 = new ReportedCastCall();
        call2.setCastCallReported(castCall);
        call2.setCastCallReporter(user);
        ReportedCastCall call3 = new ReportedCastCall();
        call3.setCastCallReported(castCall);
        call3.setCastCallReporter(user);
        List<ReportedCastCall> page = Arrays.asList(call1,call2, call3);

        Sort sort = Sort.by(castCallReportPage.getSortDirection(), castCallReportPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallReportPage.getPageNumber(), castCallReportPage.getPageSize(), sort);
        when(reportCastCallRepository.findReportedCastCallsByCastCallReportedId(castCall.getId(), pageable)).thenReturn(new PageImpl<>(page));
        Page<ReportCastCallDto> returnedPage = adminService.getAllCastCallReportedFormatted(castCall.getId(), castCallReportPage);
        assertThat(returnedPage).isNotNull();
        assertThat(returnedPage.getTotalElements()).isEqualTo(page.size());
    }

    @Test
    void flagCastCallReported() throws IOException {
        String msg = "Cast Call flagged Successfully";
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("david@gmail.com").password("password").roles("ADMIN").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        lenient().when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        User admin = new User();
        admin.setEmail("david@gmail.com");
        admin.setPassword("password");
        admin.setUserType(UserType.ADMIN);

        ReportedCastCall reportedCastCall = new ReportedCastCall();
        reportedCastCall.setId(2L);
        reportedCastCall.setCastCallReported(castCall);
        reportedCastCall.setCastCallReporter(user);
        reportedCastCall.setOtherReasons("IMPROPER");

        String castCallPublisherEmail = reportedCastCall.getCastCallReported().getPublisher().getEmail();
        EmailRequest sentNotification = new EmailRequest();
        sentNotification.setTo(castCallPublisherEmail);

        when(reportCastCallRepository.findReportedCastCallById(reportedCastCall.getId())).thenReturn(reportedCastCall);
        String flagged = adminService.flagCastCallReported(reportedCastCall.getId());
        assertThat(flagged).isEqualTo(msg);
    }

    @Test
    void unFlagCastCallReported() throws IOException {
        String msg = "Cast Call un-flagged Successfully";
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername("david@gmail.com").password("password").roles("ADMIN").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        lenient().when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        User user32 = new User();
        user.setEmail("user@gmail.com");

        User admin = new User();
        admin.setEmail("david@gmail.com");
        admin.setPassword("password");
        admin.setUserType(UserType.ADMIN);
        CastCall castCallUnFlagged = new CastCall();
        castCallUnFlagged.setReportedEnough(true);
        castCallUnFlagged.setPublisher(user32);


        ReportedCastCall reportedCastCall = new ReportedCastCall();
        reportedCastCall.setId(3L);
        reportedCastCall.setCastCallReported(castCallUnFlagged);
        reportedCastCall.setCastCallReporter(user32);
        reportedCastCall.setOtherReasons("IMPROPER");

        String castCallPublisherEmail = reportedCastCall.getCastCallReported().getPublisher().getEmail();
        EmailRequest sentNotification = new EmailRequest();
        sentNotification.setTo(castCallPublisherEmail);

        when(reportCastCallRepository.findReportedCastCallById(reportedCastCall.getId())).thenReturn(reportedCastCall);
        String unFlagged = adminService.unFlagCastCallReported(reportedCastCall.getId());
        assertThat(unFlagged).isEqualTo(msg);
    }

    @Test
    void addNewRole(){
        Role role = new Role("TEST_ROLE");
        role.setId(1L);
        role.setCreateDate(LocalDateTime.now());
        RoleDto roleDto = new RoleDto("Test_Role");
        when(mapper.map(roleDto, Role.class)).thenReturn(role);
        String resp = adminService.addRole(roleDto);
        assertThat(resp).isNotNull().isEqualTo("Role added Successfully");
        when(roleRepository.findRoleByRoleTitle(roleDto.getRoleTitle().toUpperCase())).thenReturn(Optional.of(role));
        assertThrows(BadRequestException.class, ()-> adminService.addRole(roleDto));
    }

    @Test
    void givenAdminDto_shouldCreateNewAdmin() throws IOException {
        User user = new User();
        AdminDto adminDto = new AdminDto();
        adminDto.setEmail("hopechijuk@gmail.com");
        adminDto.setPassword("HChijuka@01");
        adminDto.setGender(Gender.FEMALE);
        adminDto.setFirstName("Hope");
        adminDto.setSurname("Chijuka");
        user.setSurname(adminDto.getSurname());
        user.setEmail(adminDto.getEmail());
        user.setGender(adminDto.getGender());
        user.setPassword(adminDto.getPassword());
        user.setFirstName(adminDto.getFirstName());
        userRepository.save(user);
        when(mapper.map(adminDto, User.class)).thenReturn(user);
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        String value = adminService.createNewAdmin(adminDto);
        assertThat(value).isNotNull();
        assertEquals("Admin Successfully Created", value);
    }


    @Test
    void adminActivateDeactivate() {
        User user = new User();
        userRepository.save(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThat(adminService.activateDeactivateUser(1)).isEqualTo("User Deactivated Successfully");
        assertThat(adminService.activateDeactivateUser(1)).isEqualTo("User Activated Successfully");
    }

    @Test
    void addEquipmentType() {
        EquipmentType equipmentType= new EquipmentType("COSTUMES");
        EquipmentTypeDto equipmentTypeDto = new EquipmentTypeDto("Costumes");
        when(mapper.map(equipmentTypeDto, EquipmentType.class)).thenReturn(equipmentType);
        String responseFromAddingEquipmentType = adminService.addEquipmentType(equipmentTypeDto);
        String successAdded = "Equipment Type saved Successfully";
        assertThat(responseFromAddingEquipmentType).isNotNull().isEqualTo(successAdded);
        when(equipmentTypeRepository.findEquipmentByEquipmentType(equipmentTypeDto
                .getEquipmentType().toUpperCase())).thenReturn(Optional.of(equipmentType));
        assertThrows(BadRequestException.class, ()-> adminService.addEquipmentType(equipmentTypeDto));
    }
}