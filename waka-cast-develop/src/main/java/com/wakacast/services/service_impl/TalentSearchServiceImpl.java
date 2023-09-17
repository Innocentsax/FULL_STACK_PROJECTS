package com.wakacast.services.service_impl;

import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.models.User;
import com.wakacast.repositories.UserRepository;
import com.wakacast.repositories.criteri_class.TalentSearchCriteriaRepository;
import com.wakacast.services.TalentSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TalentSearchServiceImpl implements TalentSearchService {

    private final TalentSearchCriteriaRepository talentSearchCriteriaRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    @Cacheable(cacheNames = "talent-with-filter")
    public Page<UserResponseDto> getTalentsWithFilter(TalentSearchCriteria talentSearchCriteria) {
        return talentSearchCriteriaRepository.findTalent(talentSearchCriteria);
    }

    @Override
    @Cacheable(cacheNames = "get-all-talent")
    public Page<UserResponseDto> getAllTalents(TalentPage talentPage) {
        Sort sort = Sort.by(talentPage.getSortDirection(), talentPage.getSortBy());
        Pageable pageable = PageRequest.of(talentPage.getPageNumber(), talentPage.getPageSize(), sort);
        Page<User> talentsRegistered = userRepository.findTalentAll(pageable);
        return talentsRegistered.map(UserResponseDto::new);
    }
}
