package com.hrsupportcentresq014.services;

import com.hrsupportcentresq014.dtos.request.ChangePasswordRequest;
import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
import com.hrsupportcentresq014.dtos.request.NominationApprovalRequest;
import com.hrsupportcentresq014.dtos.request.NominationRequest;
import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;

import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.exceptions.DuplicateProcessException;

import com.hrsupportcentresq014.dtos.response.EmployeeViewProfileResponse;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface EmployeeService {
    CreateHrResponseDTO createHr (CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException;

    EmployeeProfileRequest updateEmployeeProfile(EmployeeProfileRequest employeeProfileRequest);

    String uploadDocument(MultipartFile multipartFile);

    String uploadImage(MultipartFile multipartFile);

    String uploadResume(MultipartFile multipartFile);


//    String nominate(NominationRequest request) throws DuplicateProcessException;
//
//    String approveNomination(NominationApprovalRequest request) throws Exception;

    boolean verifyCurrentPassword(Employee employee, String enteredPassword);

    String changePassword(ChangePasswordRequest req, Authentication auth);

  
    EmployeeViewProfileResponse viewProfile();

}
