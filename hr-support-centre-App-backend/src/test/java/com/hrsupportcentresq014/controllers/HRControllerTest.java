package com.hrsupportcentresq014.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrsupportcentresq014.dtos.request.JobPostingRequest;
import com.hrsupportcentresq014.dtos.response.JobPostingResponse;
import com.hrsupportcentresq014.entities.Job;
import com.hrsupportcentresq014.exceptions.EmployeeNotFoundException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.TokenRepository;
import com.hrsupportcentresq014.security_config.utils.JwtUtils;
import com.hrsupportcentresq014.services.HrService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = HRController.class)
public class HRControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HrService hrService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private TokenRepository tokenRepository;

    @InjectMocks
    private HRController hrController;

    private JacksonTester<JobPostingRequest> jsonJobPostingRequest;

    JobPostingRequest request;
    JobPostingResponse response;
    Job job;

    @BeforeEach
    public void setup(){

        JacksonTester.initFields(this, new ObjectMapper());
        request = JobPostingRequest.builder()
                .title("Senior Software Engineer")
                .departmentName("Technology")
                .closingDate("10/02/2013")
                .description("Applicant must be older than 26")
                .requirements("Applicant must know Java, AWS and Databases").build();

        job = Job.builder()
                .title("Senior Software Engineer")
                .departmentName("Technology")
                .closingDate("10/02/2013")
                .description("Applicant must be older than 26")
                .requirements("Applicant must know Java, AWS and Databases")
                .isActive(true)
                .build();

        response = JobPostingResponse.builder()
                .title("Senior Software Engineer")
                .departmentName("Technology")
                .closingDate("10/02/2013")
                .description("Applicant must be older than 26")
                .requirements("Applicant must know Java, AWS and Databases")
                .isActive(true)
                .build();
    }

    @Test
    @DisplayName("Test to create job posting")
    public void createJobPosting() throws Exception {
        Mockito.when(hrService.postJob(request)).thenReturn(response);

        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(post("/api/v1/hr/create-job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonJobPostingRequest.write(request).getJson()))
                .andReturn().getResponse();

        Mockito.verify(hrService, Mockito.times(1)).postJob(request);
        assertEquals(HttpStatus.CREATED.value(), mockHttpServletResponse.getStatus());
    }

}