package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.request.AwardRequestDTO;
import com.hrsupportcentresq014.dtos.response.AllAwardsResponseDTO;
import com.hrsupportcentresq014.dtos.response.AwardResponseDTO;
import com.hrsupportcentresq014.entities.Award;

import com.hrsupportcentresq014.exceptions.AwardsNotFoundException;
import com.hrsupportcentresq014.repositories.AwardRepository;
import com.hrsupportcentresq014.services.AwardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AwardsServiceImpl implements AwardService {

    private final AwardRepository awardsRepository;


    public AwardsServiceImpl(AwardRepository awardsRepository) {
        this.awardsRepository = awardsRepository;
    }


    @Override
    public String createAward(AwardRequestDTO awardRequestDTO) throws AwardsNotFoundException{




        Award awards = new Award();
        awards.setTitle(awardRequestDTO.getTitle());
        awards.setDescription(awardRequestDTO.getDescription());
        awards.setYear(LocalDate.now().getYear());
        Award savedAward = awardsRepository.save(awards);

        return "Award created successfully: " + savedAward.getTitle();
    }

    @Override
    public Page<AwardResponseDTO> getAwardByYear(String year, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Award> awardsPage = awardsRepository.findAwardByYear(Integer.parseInt(year), pageable);

        if (awardsPage.isEmpty()) {
            throw new AwardsNotFoundException(String.format("Award not found for year %s", year));
        }

        return awardsPage.map(this::toResponseDTO);
    }

    private AwardResponseDTO toResponseDTO(Award award) {
        return AwardResponseDTO.builder()
                .title(award.getTitle())
                .description(award.getDescription())
                .year(award.getYear())
                .build();
    }

    @Override
    public AllAwardsResponseDTO getAllRewards(int pageNo, int pageSize) {

        Sort sort = Sort.by(Sort.Direction.DESC,"year");
        PageRequest paging =  PageRequest.of(pageNo,pageSize,sort);
        Page<Award> pageRewards = awardsRepository.findAll(paging);
        List<Award> awardList = pageRewards.getContent();

        var rewards = AllAwardsResponseDTO.builder()
                .awardList(awardList)
                .currentPage(pageRewards.getNumber())
                .totalElement(pageRewards.getTotalElements())
                .totalPage(pageRewards.getTotalPages())
                .build();

        return rewards;
    }
}

