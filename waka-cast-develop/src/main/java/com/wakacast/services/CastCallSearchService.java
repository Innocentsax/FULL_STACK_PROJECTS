package com.wakacast.services;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.search_criteria.CastCallSearchCriteria;
import org.springframework.data.domain.Page;

public interface CastCallSearchService {
    Page<CastCallDto> getCastCallSearch(CastCallPage castCallPage, CastCallSearchCriteria searchCriteria);
}
