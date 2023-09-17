package com.wakacast.services.service_impl;

import com.cloudinary.Cloudinary;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.*;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.enums.LanguageProficiency;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.*;
import com.wakacast.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.wakacast.enums.UserType.SUBSCRIBED;
import static com.wakacast.enums.UserType.UNSUBSCRIBED;
import static com.wakacast.models.Equipment_.TYPE_OF_ASSET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.core.userdetails.User.withUsername;

@ExtendWith(MockitoExtension.class)
class UserServicesImplTest {
    @Mock
    private ModelMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenUtil tokenUtil;
    @Mock
    private EmailService emailService;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private SpokenLanguageRepository languageRepository;
    @Mock
    private Cloudinary cloudinary;
    @Mock
    private PortfolioRepository portfolioRepository;
    @Mock
    private UserPersonaRepository userPersonaRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private SearchAgentRepository searchAgentRepository;
    @InjectMocks
    private UserServicesImpl userServices;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void signup() throws IOException {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("obum@gmail.com");
        dto.setUserPersona("TALENT");
        dto.setPassword("12345667");
        dto.setConfirmPassword("12345667");

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserType(UNSUBSCRIBED);
        when(mapper.map(dto, User.class)).thenReturn(user);
        String returnedValue = userServices.signup(dto);
        assertThat(returnedValue).isNotNull().isEqualTo("Registration successful, check your email for verification");
    }

    @Test
    void verifyAccount() {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("obum@gmail.com");
        dto.setUserPersona("TALENT");
        dto.setPassword("12345667");
        dto.setConfirmPassword("12345667");

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        String token = "token";

        assertThat(user.isAccountVerified()).isFalse();

        when(tokenUtil.getUserEmailFromToken(token)).thenReturn(dto.getEmail());
        when(userRepository.findUserByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        userServices.verifyAccount(token);

        assertThat(user.isAccountVerified()).isTrue();
    }

    @Test
    void setUpProfile() {
        ProfileSetupDto profileSetupDto = new ProfileSetupDto();
        profileSetupDto.setFirstName("Innocent");
        profileSetupDto.setSurname("Ogesiano");
        profileSetupDto.setDateOfBirth(LocalDate.of(2000, 10, 31));
        Set<String> personas = new HashSet<>();
        personas.add("talent");
        profileSetupDto.setUserPersonas(personas);
        Set<String> roleTitles = new HashSet<>();
        roleTitles.add("Voice-over Artiste");
        profileSetupDto.setRoles(roleTitles);
        Set<String> genreTitles = new HashSet<>();
        genreTitles.add("Movie Production");
        profileSetupDto.setGenres(genreTitles);
        SpokenLanguageDto languageDto = new SpokenLanguageDto();
        languageDto.setLanguage("English");
        languageDto.setProficiency(LanguageProficiency.ADVANCED);
        Set<SpokenLanguageDto> languageDtos = new HashSet<>();
        languageDtos.add(languageDto);
        profileSetupDto.setSpokenLanguages(languageDtos);
        String email = "inno@gmail.com";

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("inno@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        User user = new User();
        user.setFirstName(profileSetupDto.getFirstName());
        user.setSurname(profileSetupDto.getSurname());
        user.setDateOfBirth(profileSetupDto.getDateOfBirth());
        user.setEmail(email);
        user.setUserPersonas(new HashSet<>());

        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

        String returnedValue = userServices.setUpProfile(profileSetupDto);
        assertThat(returnedValue).isNotNull().isEqualTo("Profile successfully updated");
    }

    @Test
    void postEquipmentForLease() {
        String email = "inno@gmail.com";

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("inno@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        User user = new User();
        user.setFirstName("Inno");
        user.setSurname("Inno");
        user.setDateOfBirth(LocalDate.of(2000, 10, 31));
        user.setEmail(email);
        user.setUserPersonas(new HashSet<>());

        EquipmentRequestedDto equipmentRequestedDto = new EquipmentRequestedDto();
        equipmentRequestedDto.setEquipmentName("CAMERA");
        equipmentRequestedDto.setCity("BENIN");
        equipmentRequestedDto.setState("EDO");

        Equipment newEquipment = new Equipment();
        newEquipment.setEquipmentOwner(user);

        when(mapper.map(equipmentRequestedDto, Equipment.class)).thenReturn(newEquipment);
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));
        String returnedValue = userServices.postEquipmentForLease(equipmentRequestedDto);
        assertThat(returnedValue).isNotNull().isEqualTo("Equipment listed successfully");
    }

    @Test
    void deletePostedEquipment() {
        User user = new User();
        user.setEmail("og@gmail.com");
        Equipment equipment = new Equipment();
        equipment.setEquipmentOwner(user);
        equipment.setEquipmentName("Camera");

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("og@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(equipmentRepository.findEquipmentByIdAndEquipmentOwnerEmail(1L, user.getEmail())).
                thenReturn(Optional.of(equipment));
        String returnedValue = userServices.deletePostedEquipment(1L);
        assertThat(returnedValue).isNotNull().isEqualTo("Equipment/facility deleted successfully");
    }

    @Test
    void deleteUserAccount() {
        User user = new User();
        user.setEmail("ony@gmail.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername(user.getEmail()).password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        String value = userServices.deleteUserAccount();
        assertThat(value).isNotNull().isEqualTo("User account deleted successfully");
    }

    @Test
    void setUpSearchAgent() {
        User user = new User();
        user.setEmail("og@gmail.com");
        Set<UserPersona> personas = new HashSet<>();
        personas.add(new UserPersona("talent"));
        user.setUserPersonas(personas);
        SearchAgent searchAgent = new SearchAgent();
        searchAgent.setUser(user);
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername(user.getEmail()).password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(mapper.map(roleMatchSearchCriteria, SearchAgent.class)).thenReturn(searchAgent);
        String returnedValue = userServices.setUpSearchAgent(roleMatchSearchCriteria);
        assertThat(returnedValue).isNotNull().isEqualTo("Search Agent set up successfully");
    }

    @Test
    void editSearchAgent() {
        User user = new User();
        user.setEmail("og@gmail.com");
        Set<UserPersona> personas = new HashSet<>();
        personas.add(new UserPersona("talent"));
        user.setUserPersonas(personas);
        SearchAgent searchAgent = new SearchAgent();
        searchAgent.setUser(user);
        searchAgent.setId(1L);
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("og@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(searchAgentRepository.findSearchAgentByIdAndUserEmail(1L, "og@gmail.com")).thenReturn(Optional.of(searchAgent));

        String returnedValue = userServices.editSearchAgent(1L,roleMatchSearchCriteria);
        assertThat(returnedValue).isNotNull().isEqualTo("Search Agent updated successfully");
    }

    @Test
    void logout() {
        User user = new User();
        user.setEmail("ony@gmail.com");
        LogOutRequestDto logOutRequest = new LogOutRequestDto();
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.
                withUsername(user.getEmail()).password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        String value = userServices.logout(logOutRequest);
        assertThat(value).isNotNull().isEqualTo("User has successfully logged out from the system!");
    }

    @Test
    void removeSearchAgent() {
        User user = new User();
        user.setEmail("og@gmail.com");
        Set<UserPersona> personas = new HashSet<>();
        personas.add(new UserPersona("talent"));
        user.setUserPersonas(personas);
        SearchAgent searchAgent = new SearchAgent();
        searchAgent.setUser(user);
        searchAgent.setId(1L);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("og@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(searchAgentRepository.findSearchAgentByIdAndUserEmail(1L, "og@gmail.com")).thenReturn(Optional.of(searchAgent));

        String returnedValue = userServices.removeSearchAgent(1L);
        assertThat(returnedValue).isNotNull().isEqualTo("Search Agent removed successfully");
    }

    @Test
    void getLoggedInUser() {
        User user = new User();
        user.setEmail("og@gmail.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("og@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());

        when(mapper.map(user, UserDto.class)).thenReturn(dto);

        UserDto userDto = userServices.getLoggedInUser();
        assertThat(userDto).isNotNull();
        assertThat(userDto.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void givenInvalidEmail_shouldThrow_UserWithEmailNotFoundException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("og@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(userRepository.findUserByEmail("og@gmail.com")).thenReturn(Optional.empty());

        assertThrows(UserWithEmailNotFound.class, ()-> userServices.getLoggedInUser());
    }


    @Test
    void addSuperAdmin() throws IOException {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("testAdmin@gmail.com");
        dto.setUserPersona("ADMIN");
        dto.setPassword("1234567");
        dto.setConfirmPassword("1234567");


        when(userRepository.findUserByEmail(dto.getEmail())).thenReturn(Optional.empty());

        String response = userServices.addSuperAdmin(dto);
        assertThat(response).isNotNull().isEqualTo("Successfully added a Super_Admin");
    }


    @Test
    void addRoleToUser() throws RoleNotFoundException {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUserType(SUBSCRIBED);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setAccountVerified(true);
        user.setFirstName("SUPER_ADMIN");
        Role role = new Role("TEST_ROLE");

        RoleUserDto roleUserDto = new RoleUserDto(role.getRoleTitle(), user.getEmail());

        when(userRepository.findUserByEmail(roleUserDto.getUserEmail())).thenReturn(Optional.of(user));
        when(roleRepository.findRoleByRoleTitle(roleUserDto.getRoleTitle())).thenReturn(Optional.of(role));
        String response = userServices.addRoleToUser(roleUserDto);
        assertThat(response).isNotNull().isEqualTo("Role " +roleUserDto.getRoleTitle() + " added successfully to user " +roleUserDto.getUserEmail());
    }

    @Test
    void getUserById() throws ResourceNotFound {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setUserType(SUBSCRIBED);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setAccountVerified(true);
        user.setFirstName("SUPER_ADMIN");

        UserDto userDto = new UserDto();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);
        UserDto userDtoById = userServices.getUserById(user.getId());
        assertThat(userDtoById).isNotNull();
    }
}