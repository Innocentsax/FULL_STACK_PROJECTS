package com.wakacast.services.service_impl;

import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.enums.Gender;
import com.wakacast.models.CastCall;
import com.wakacast.models.Role;
import com.wakacast.models.User;
import com.wakacast.models.UserPersona;
import com.wakacast.repositories.CastCallRepository;
import com.wakacast.repositories.RoleRepository;
import com.wakacast.repositories.UserRepository;
import com.wakacast.repositories.criteri_class.CastCallSearchCriteriaRepository;
import com.wakacast.repositories.criteri_class.UserSearchCriteriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.withUsername;

@ExtendWith(MockitoExtension.class)
class RoleSearchServiceImplTest {
    @Mock
    private CastCallSearchCriteriaRepository castCallSearchCriteriaRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CastCallRepository castCallRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserSearchCriteriaRepository userSearchCriteriaRepository;
    @InjectMocks
    private RoleSearchServiceImpl roleSearchService;

    private CastCallPage castCallPage;
    private Page<CastCall> page;
    private User user;
    private List<CastCall> castCalls;

    private TalentPage talentPage;
    private Page<UserResponseDto> userPage;
    List<UserResponseDto> listOfUsers;






    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("onychris@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setPassword("ONYchris@007");
        UserPersona persona = new UserPersona("Talent");
        Set<UserPersona> userPersonas = new HashSet<>();
        userPersonas.add(persona);
        Role role = new Role("Actor");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setUserPersonas(userPersonas);
        user.setRoles(roles);

        CastCall castCall = new CastCall();
        castCall.setCreateDate(LocalDateTime.now().minusHours(12));
        castCall.setProjectName("Wakcast TV series");
        castCall.setProjectType("TV series");
        castCall.setTalentSkill("Actor");
        castCall.setState("Lagos");
        castCall.setCity("Lagos");
        castCall.setCountry("Nigeria");
        castCall.setPostExpiryDate(LocalDate.now());
        castCall.setPublisher(user);
        castCalls = new ArrayList<>();
        castCalls.add(castCall);
        castCallPage = new CastCallPage();
        page = new PageImpl<>(castCalls);

        talentPage = new TalentPage();
        listOfUsers = new ArrayList<>();
        userPage = new PageImpl<>(listOfUsers);

    }

    @Test
    void getCastCallMatchingRoles() {
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();
        when(castCallSearchCriteriaRepository.findMatchingRoles(castCallPage, roleMatchSearchCriteria)).thenReturn(page);
        Page<CastCall> returnedPage = roleSearchService.getCastCallMatchingRoles(castCallPage, roleMatchSearchCriteria);
        assertThat(returnedPage).isNotNull().isEqualTo(page);
    }

    @Test
    void getSuggestedCastCallsBasedOnUserProfile() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        UserDetails userDetails = withUsername("onychris@gmail.com").password("password").roles("USER").build();
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext);
        when((UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).thenReturn(userDetails);

        when(userRepository.findUserByEmail("onychris@gmail.com")).thenReturn(Optional.of(user));
        lenient().when(castCallRepository.findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndIsReportedEnough(
                LocalDateTime.now().minusHours(12), "Actor", false
        )).thenReturn(castCalls);

        Page<CastCall> castCallPage = roleSearchService.getSuggestedCastCallsBasedOnUserProfile();
        assertThat(castCallPage).isNotNull();
    }

    @Test
    void getAllRolesAndById() {
        Long id = 1L;
        Role role = new Role("TEST_ROLE");
        when(roleRepository.findRoleById(id)).thenReturn(Optional.of(role));
        Role returnedRole = roleSearchService.getRoleById(id);
        assertThat(returnedRole).isNotNull().isEqualTo(role);

        Role role1 = new Role("TEST_ROLE1");
        Role role2 = new Role("TEST_ROLE2");
        List<Role> roleList = Arrays.asList(role, role1, role2);
        Page<Role> rolePage = new PageImpl<>(roleList);
        when(roleRepository.findAll()).thenReturn(roleList);
        Page<Role> returnedPage = roleSearchService.getAllRoles();
        assertThat(returnedPage).isNotNull().isEqualTo(rolePage);

    }

    @Test
    void getUsersByRole() {
        UserRoleSearchDTO userRoleSearch = new UserRoleSearchDTO();
        userRoleSearch.setRoleOfUser("ACTOR");

        when(userSearchCriteriaRepository.findUserByRole(userRoleSearch)).thenReturn(userPage);
        Page<UserResponseDto> userPage = roleSearchService.getUsersByRole(userRoleSearch);
        assertThat(userPage).isNotNull().isEqualTo(userPage);
    }
}