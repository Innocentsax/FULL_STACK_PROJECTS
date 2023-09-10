package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AllAwardsResponseDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;

public interface AwardService {
    AllAwardsResponseDTO getAllRewards(int pageNo, int pageSize);

    String createAward(AwardRequestDTO awardRequestDTO) throws AccessDeniedException;

    Page<AwardResponseDTO> getAwardByYear(String year, int page, int size);
}
