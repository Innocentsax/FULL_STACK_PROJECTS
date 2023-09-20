package com.decagon.lendingservice.service.serviceImpl;



import com.decagon.lendingservice.dto.InvestmentDTORequest;
import com.decagon.lendingservice.dto.InvestmentDTOResponse;
import com.decagon.lendingservice.entity.InvestmentPreference;
import com.decagon.lendingservice.exceptions.BorrowersNotAllowedException;
import com.decagon.lendingservice.exceptions.InvestmentPreferenceExistsException;
import com.decagon.lendingservice.repo.InvestmentPreferenceRepository;
import com.decagon.lendingservice.utils.JwtUtils;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InvestmentPreferenceServiceImplTest {
    @Mock
    private InvestmentPreferenceRepository investmentRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private InvestmentPreferenceServiceImpl investmentPreferenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateInvestment_UserIsBorrower_ThrowsBorrowersNotAllowedException() {
        // Arrange
        String token = "valid_jwt_token";
        InvestmentDTORequest request = new InvestmentDTORequest(BigDecimal.valueOf(1000), BigDecimal.valueOf(5),
                3, 30);
        when(jwtUtils.getUserTypeFromToken(token)).thenReturn("borrower");

        // Act & Assert
        assertThrows(BorrowersNotAllowedException.class, () -> investmentPreferenceService.createInvestment(request, token));

    }


    @Test
    public void testCreateInvestment_InvalidLoanAmount_ThrowsValidationException() {
        // Arrange
        String token = "valid_jwt_token";
        InvestmentDTORequest request = new InvestmentDTORequest(BigDecimal.valueOf(0), BigDecimal.valueOf(5),
                3, 30);
        when(jwtUtils.getUserTypeFromToken(token)).thenReturn("lender");

        // Act & Assert
        assertThrows(ValidationException.class, () -> investmentPreferenceService.createInvestment(request, token));

    }

    @Test
    public void testCreateInvestment_ValidInput_SuccessfullyCreatesInvestment() {
        // Arrange
        String token = "valid_jwt_token";
        InvestmentDTORequest request = new InvestmentDTORequest(BigDecimal.valueOf(1000), BigDecimal.valueOf(5),
                3, 30);
        String userId = "8902c64a-13a8-4796-92a5-d64701e384f1";
        when(jwtUtils.getUserTypeFromToken(token)).thenReturn("lender");
        when(jwtUtils.extractUserIdFromToken(token)).thenReturn(userId);
        InvestmentPreference investmentPreference = new InvestmentPreference();
        when(modelMapper.map(request, InvestmentPreference.class)).thenReturn(investmentPreference);
        when(investmentRepository.save(investmentPreference)).thenReturn(investmentPreference);

        // Act
        InvestmentDTOResponse response = investmentPreferenceService.createInvestment(request, token);

        // Assert
        assertNotNull(response);
        assertEquals(investmentPreference.getLoanAmount(), response.getLoanAmount());
        assertEquals(investmentPreference.getInterestRate(), response.getInterestRate());
        assertEquals(investmentPreference.getRiskTolerance(), response.getRiskTolerance());
        assertEquals(investmentPreference.getDurationInDays(), response.getDurationInDays());
    }

}