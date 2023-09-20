package com.decagon.service;

import com.decagon.domain.screen.ContactInformation;
import com.decagon.dto.pojoDTO.*;
import com.decagon.dto.response.ProfileResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ProfileResponseDTO getProfile(String authorizationHeader);
    ProfileResponseDTO createProfile(String user_id, ContactInformationDTO contactInformation) throws JsonProcessingException;

    String updateContactInformation(ContactInformationDTO contactInfo, String authorizationHeader);

    ProfileResponseDTO updateEmploymentStatus(EmploymentStatusDTO employmentStatus, String authorizationHeader);

    ProfileResponseDTO updateGovernmentID(GovernmentIDDTO governmentID, MultipartFile file, String authorizationHeader);

    ProfileResponseDTO updateIncomeStatus(IncomeStatusDTO incomeStatus, String authorizationHeader);
    ContactInformation getContact(String auth);

    ProfileResponseDTO updateBankAccount(BankAccountDTO bankAccount, String authorizationHeader);

    ProfileResponseDTO updateProofOfAddress(ProofOfAddressDTO proofOfAddress, MultipartFile file, String authorizationHeader);
}