package com.wakacast.services;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.ReasonForReportingCastCallDto;
import com.wakacast.dto.pages_criteria.ApplicantPage;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.models.CastCall;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public interface CastCallService {
    String postCastCall(CastCallDto castCallDto);
    String editCastCall(CastCallDto castCallDto, long castCallId);
    String deleteCastCall(long castCallId);
    Page<CastCall> getAllActiveCastCalls(CastCallPage castCallPage);
    String applyForCastCall(long castCallId) throws IOException;
    Page<CastCallDto> getAllCastCallsByAProducer(CastCallPage castCallPage);
    String reportCastCall(long castCallId, ReasonForReportingCastCallDto reasonForReportingCastCallDto) throws IOException;
    CastCallDto getCastCallById(Long id);
    Page<UserResponseDto> getCastCallApplicants(long castCallId, ApplicantPage applicantPage);
}
