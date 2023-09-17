package com.wakacast.services.service_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.search_criteria.CastCallSearchCriteria;
import com.wakacast.repositories.criteri_class.CastCallSearchCriteriaRepository;
import com.wakacast.services.CastCallSearchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CastCallSearchServiceImpl implements CastCallSearchService {
    private final CastCallSearchCriteriaRepository criteriaRepository;
    private final ModelMapper mapper;

    @Override
    @Cacheable(cacheNames = "get-cast-call-search")
    public Page<CastCallDto> getCastCallSearch(CastCallPage castCallPage, CastCallSearchCriteria searchCriteria) {
        return criteriaRepository.findCastCall(castCallPage, searchCriteria).map(x -> mapper.map(x, CastCallDto.class));
    }
}
