package com.wakacast.controllers;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.ReasonForReportingCastCallDto;
import com.wakacast.dto.pages_criteria.ApplicantPage;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.models.CastCall;

import com.wakacast.responses.ResponseData;
import com.wakacast.services.CastCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.wakacast.controllers.UserController.response;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class CastCallController {
    private final CastCallService castCallService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/post-cast-call")
    public ResponseEntity<ResponseData> postCastCall(@Valid @RequestBody CastCallDto castCall) {
        return response(HttpStatus.CREATED, castCallService.postCastCall(castCall));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/edit-cast-call/{castCallId}")
    public ResponseEntity<ResponseData> editCastCall(@Valid @RequestBody CastCallDto castCallDto,
                                                     @PathVariable long castCallId) {
        return response(HttpStatus.OK, castCallService.editCastCall(castCallDto, castCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/delete-cast-call/{castCallId}")
    public ResponseEntity<ResponseData> deleteCastCall(@PathVariable long castCallId) {
        return response(HttpStatus.OK, castCallService.deleteCastCall(castCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-active-cast-calls")
    public ResponseEntity<Page<CastCall>> getAllActiveCastCalls(CastCallPage castCallPage) {
        return new ResponseEntity<>(castCallService.getAllActiveCastCalls(castCallPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-cast-call-by-id/{id}")
    public ResponseEntity<CastCallDto> getCastCallById(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(castCallService.getCastCallById(id),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping( "/apply-for-cast-call/{castCallId}")
    public ResponseEntity<ResponseData> applyForCastCall(@PathVariable long castCallId) throws IOException {
        return response(OK, castCallService.applyForCastCall(castCallId));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/view-cast-calls")
    public ResponseEntity<Page<CastCallDto>> viewAllCastCallsByAProducer(CastCallPage castCallPage) {
        return new ResponseEntity<>(castCallService.getAllCastCallsByAProducer(castCallPage), OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping( "/report-cast-call/{castCallId}")
    public ResponseEntity<ResponseData> reportCastCall(@PathVariable long castCallId,
                                                     @Valid @RequestBody ReasonForReportingCastCallDto reason) throws IOException {
        return response(OK, castCallService.reportCastCall(castCallId, reason));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping( "/get-cast-call-applicant/{castCallId}")
    public ResponseEntity<Page<UserResponseDto>> getCastCallApplicants(@PathVariable long castCallId, ApplicantPage applicantPage) {
        return new ResponseEntity<>(castCallService.getCastCallApplicants(castCallId, applicantPage), OK);
    }
}