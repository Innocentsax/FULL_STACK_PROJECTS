package com.wakacast.controllers;

import com.wakacast.dto.*;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.CastCallReportPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.models.EquipmentType;
import com.wakacast.models.Role;
import com.wakacast.responses.HttpResponse;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all-users")
    public ResponseEntity<Page<UserDto>> getAllUsers(UserPage userPage) {
        return new ResponseEntity<>(adminService.getAllUsers(userPage), OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-genre")
    public ResponseEntity<ResponseData> addNewGenre(@RequestBody GenreDto genreDto) {
        return response(adminService.addGenre(genreDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/send-email")
    public ResponseEntity<ResponseData> sendEmail(@RequestBody DailyEmailDTO emailDTO) throws IOException {
        return response(adminService.sendDailyEmails(emailDTO));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-language")
    public ResponseEntity<ResponseData> addNewLanguage(@RequestBody LanguageDto languageDto) {
        return response(adminService.addLanguage(languageDto));
    }

    private ResponseEntity<ResponseData> response(String message){
        return new ResponseEntity<>(new ResponseData(new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(), message.toUpperCase())), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete-cast-call-admin/{castCallId}")
    public ResponseEntity<ResponseData> deleteCastCallAdmin(@Valid @PathVariable long castCallId) {
        return response(adminService.deleteCastCallAdmin(castCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-all-cast-calls-admin")
    public ResponseEntity<Page<CastCallDtoAdmin>> getAllCastCallAdmin(CastCallPage castCallPage) {
        return new ResponseEntity<>(adminService.getAllCastCalls(castCallPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-reason-for-reporting-cast-call")
    public ResponseEntity<ResponseData> addReasonForReportingCAstCall (@RequestBody ReasonToSaveDto reasonToSaveDto) {
        return response(adminService.addReasonForReportingCastCall(reasonToSaveDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-cast-calls-reported")
    public ResponseEntity<Page<ReportCastCallDto>> getAllCastCallReported (Long reportedCastCallId, CastCallReportPage castCallReportPage) {
        return new ResponseEntity<>(adminService.getAllCastCallReportedFormatted(reportedCastCallId, castCallReportPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/flag-cast-call-reported/{reportedCastCallId}")
    public ResponseEntity<ResponseData> flagReportedCastCallAdmin(@Valid @PathVariable long reportedCastCallId) throws IOException {
        return response(adminService.flagCastCallReported(reportedCastCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/un-flag-cast-call-reported/{reportedCastCallId}")
    public ResponseEntity<ResponseData> unFlagReportedCastCallAdmin(@Valid @PathVariable long reportedCastCallId) throws IOException {
        return response(adminService.unFlagCastCallReported(reportedCastCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/add-new-role")
    public ResponseEntity<ResponseData> addNewRole (@RequestBody RoleDto roleDto){
        return response(adminService.addRole(roleDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/activate-deactivate-user/{id}")
    public ResponseEntity<ResponseData> activateDeactivateUser(@PathVariable long id) {
        return response(adminService.activateDeactivateUser(id));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add-equipment-type-to-db")
    public ResponseEntity<ResponseData> addEquipmentType(@RequestBody EquipmentTypeDto equipmentTypeDto) {
        return response(adminService.addEquipmentType(equipmentTypeDto));
    }
}

