package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.JobSearchRequest;
import com.hrsupportcentresq014.dtos.response.JobResponseDto;
import com.hrsupportcentresq014.dtos.response.JobSearchResponse;
import com.hrsupportcentresq014.exceptions.NoJobsFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface JobService {
    Page<JobSearchResponse> filterJobs(String keywords, String filter, String department, Pageable pageable) throws NoJobsFoundException;
    Page<JobSearchResponse> filterAllJobs(String keywords, String filter, String department, Pageable pageable) throws NoJobsFoundException;

}
