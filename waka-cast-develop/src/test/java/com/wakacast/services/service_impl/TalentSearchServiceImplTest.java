package com.wakacast.services.service_impl;

import com.wakacast.dto.CastCallDtoAdmin;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.enums.Gender;
import com.wakacast.models.CastCall;
import com.wakacast.models.User;
import com.wakacast.models.UserPersona;
import com.wakacast.repositories.UserRepository;
import com.wakacast.repositories.criteri_class.TalentSearchCriteriaRepository;
import com.wakacast.services.TalentSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TalentSearchServiceImplTest {
    @Mock
    private TalentSearchCriteriaRepository talentSearchCriteriaRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TalentSearchService talentService;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private TalentSearchServiceImpl talentSearchService;

    private TalentPage talentPage;
    private Page<UserResponseDto> page;
    List<UserResponseDto> list;

    @BeforeEach
    void setUp() {
        talentPage = new TalentPage();
        list = new ArrayList<>();
        page = new PageImpl<>(list);

    }

    @Test
    void getTalentsWithFilter() {
        TalentSearchCriteria talentSearchCriteria = new TalentSearchCriteria();
        talentSearchCriteria.setKeyword("Actor");
        talentSearchCriteria.setAllAuditionsAndJob("Stage Presenter");
        talentSearchCriteria.setState("Lagos");
        talentSearchCriteria.setGender(Gender.FEMALE);

        when(talentSearchCriteriaRepository.findTalent(talentSearchCriteria)).thenReturn(page);
        Page<UserResponseDto> resultPage = talentSearchService.getTalentsWithFilter(talentSearchCriteria);
        assertThat(resultPage).isNotNull().isEqualTo(page);
    }

    @Test
    void getAllTalents() {
        TalentPage talentPage = new TalentPage();
        UserPersona pasona1 = new UserPersona();
        pasona1.setPersona("Talent");
        Set<UserPersona> pas = new HashSet<>();
        pas.add(pasona1);

        User user1 = new User();
        user1.setUserPersonas(pas);
        User user2 = new User();
        user2.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Producer"))));
        User user3 = new User();
        user3.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Talent"))));
        User user4 = new User();
        user4.setUserPersonas(new HashSet<>(Set.of(new UserPersona("Crew"))));

        List<User> list = Arrays.asList(user1, user2, user3, user4);
        Sort sort = Sort.by(talentPage.getSortDirection(), talentPage.getSortBy());
        Pageable pageable = PageRequest.of(talentPage.getPageNumber(), talentPage.getPageSize(), sort);
        when(userRepository.findTalentAll(pageable)).thenReturn(new PageImpl<>(list));
        Page<UserResponseDto> returnedPage = talentSearchService.getAllTalents(talentPage);
        assertThat(returnedPage).isNotNull();
        assertThat(returnedPage.getTotalElements()).isEqualTo(list.size());
        System.out.println(returnedPage.getTotalElements());
    }
}