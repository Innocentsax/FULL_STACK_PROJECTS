package com.wakacast.services;

import com.wakacast.dto.*;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.CastCallReportPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.responses.JwtResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface AdminService {
    Page<UserDto> getAllUsers(UserPage userPage);
    ResponseEntity<JwtResponse> generateLoginToken(String email) throws IOException;
    String confirmLoginToken (String token);
    String addGenre(GenreDto genreDto);
    String deleteCastCallAdmin(long castCallId);
    Page<CastCallDtoAdmin> getAllCastCalls(CastCallPage castCallPage);
    String sendDailyEmails(DailyEmailDTO emailDTO) throws IOException;
    String addLanguage(LanguageDto language);
    String addReasonForReportingCastCall (ReasonToSaveDto reasonToSaveDto);
    Page<ReportCastCallDto> getAllCastCallReportedFormatted (Long reportedCastCallId, CastCallReportPage castCallReportPage);
    String flagCastCallReported(long reportedCastCall) throws IOException;
    String unFlagCastCallReported(long reportedCastCall) throws IOException;
    String addRole(RoleDto roleDto);
    String createNewAdmin(AdminDto adminDto) throws IOException;
    String activateDeactivateUser(long id);
    String addEquipmentType(EquipmentTypeDto equipmentTypeDto);
}
   
