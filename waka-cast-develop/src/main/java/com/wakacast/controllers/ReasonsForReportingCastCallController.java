package com.wakacast.controllers;

import com.wakacast.models.ReasonForReportingCastCall;
import com.wakacast.repositories.ReasonForReportingCastCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reason")
public class ReasonsForReportingCastCallController {
    private final ReasonForReportingCastCallRepository reasonForReportingCastCallRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-all-reasons")
    public ResponseEntity<List<ReasonForReportingCastCall>> getReasonsForReporting () {
        return new ResponseEntity<>(reasonForReportingCastCallRepository.findAll(),  HttpStatus.OK);
    }
}
