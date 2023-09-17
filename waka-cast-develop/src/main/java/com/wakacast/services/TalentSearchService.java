package com.wakacast.services;

import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TalentSearchService {
    Page<UserResponseDto> getTalentsWithFilter(TalentSearchCriteria talentSearchCriteria);
    Page<UserResponseDto> getAllTalents(TalentPage talentPage);
}
