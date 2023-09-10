package com.hrsupportcentresq014.controllers;

import com.hrsupportcentresq014.dtos.response.JobSearchResponse;
import com.hrsupportcentresq014.exceptions.NoJobsFoundException;
import com.hrsupportcentresq014.services.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job")
public class JobController {
    private JobService jobService;
    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @GetMapping("/filter")
    public ResponseEntity<Page<JobSearchResponse>> filterJob(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "6") Integer size,
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "filter", defaultValue = "newest") String filter,
            @RequestParam(value = "department", required = false) String department) throws NoJobsFoundException {
        Pageable pageable = PageRequest.of(page, size);
         return ResponseEntity.ok(jobService.filterJobs(keywords, filter, department, pageable));
    }
}
