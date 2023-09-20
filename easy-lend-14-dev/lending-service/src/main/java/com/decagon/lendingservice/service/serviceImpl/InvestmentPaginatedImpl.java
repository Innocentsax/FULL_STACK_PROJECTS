package com.decagon.lendingservice.service.serviceImpl;

import com.decagon.lendingservice.entity.InvestmentPreference;
import com.decagon.lendingservice.dto.InvestmentDTOResponse;
import com.decagon.lendingservice.repo.InvestmentPreferencePaginationRepository;
import com.decagon.lendingservice.service.InvestmentPreferencePagination;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvestmentPaginatedImpl implements InvestmentPreferencePagination {
    private final InvestmentPreferencePaginationRepository paginationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<InvestmentDTOResponse> getPaginatedInvestment(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<InvestmentPreference>  investmentPage = paginationRepository.findAll(pageRequest);
        return investmentPage.map(this::dtoResponse);
    }
    //Helper Method to convert InvestmentPreference to InvestmentDTO
    private InvestmentDTOResponse  dtoResponse(InvestmentPreference investmentPreference){
        return modelMapper.map(investmentPreference, InvestmentDTOResponse.class);
    }
}
