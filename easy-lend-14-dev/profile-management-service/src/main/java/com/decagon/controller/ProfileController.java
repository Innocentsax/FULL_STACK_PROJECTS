package com.decagon.controller;

import com.decagon.domain.screen.ContactInformation;
import com.decagon.dto.pojoDTO.*;
import com.decagon.dto.response.ApiResponse;
import com.decagon.dto.response.ProfileResponseDTO;
import com.decagon.service.ProfileService;
import com.decagon.utils.JwtUtils;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@Tag(name = "Profile API")
public class ProfileController {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final ProfileService profileService;
    private final JwtUtils jwtUtils;

    public ProfileController(ProfileService profileService, JwtUtils jwtUtils) {
        this.profileService = profileService;
        this.jwtUtils = jwtUtils;
    }

    @CrossOrigin("http://localhost:5173")
    @GetMapping("/get-profile")
    @Operation(summary = "Fetch user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getProfile(
            @Parameter(description = "user profile is fetched", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.getProfile(authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:5173")
    @PutMapping("/contact-information")
    @Operation(summary = "Update contact information for a user profile")
    public ResponseEntity<ApiResponse<String>> updateContactInformation(
            @Parameter(description = "Contact information to update", required = true)
            @RequestBody ContactInformationDTO contactInfo,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<String> responseDTO = new ApiResponse<>(profileService.updateContactInformation(contactInfo, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @PutMapping("/employment-status")
    @Operation(summary = "Update employment status for a user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateEmploymentStatus(
            @Parameter(description = "Employment status to update", required = true)
            @RequestBody EmploymentStatusDTO employmentStatusDTO,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.updateEmploymentStatus(employmentStatusDTO, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @PostMapping(value = "/government-id", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update government ID information for a user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateGovernmentID(
            @Parameter(description = "Government ID information to update", required = true)
            @RequestParam("GovernmentIDDTO") String governmentIDDTO,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {
        GovernmentIDDTO governmentIDDTO1 = new Gson().fromJson(governmentIDDTO,GovernmentIDDTO.class);

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.updateGovernmentID(governmentIDDTO1, file, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @PutMapping("/income-status")
    @Operation(summary = "Update income status for a user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateIncomeStatus(
            @Parameter(description = "Income status to update", required = true)
            @RequestBody IncomeStatusDTO incomeStatusDTO,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.updateIncomeStatus(incomeStatusDTO, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @CrossOrigin("http://localhost:5173")
    @PutMapping("/bank-account")
    @Operation(summary = "Update bank account information for a user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateBankAccount(
            @Parameter(description = "Bank account information to update", required = true)
            @RequestBody BankAccountDTO bankAccountDTO,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.updateBankAccount(bankAccountDTO, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:5173")
    @PutMapping(value = "/proof-of-address", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update proof of address information for a user profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> updateProofOfAddress(
            @Parameter(description = "Proof of address information to update", required = true)
            @ModelAttribute ProofOfAddressDTO proofOfAddressDTO,
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ProfileResponseDTO> responseDTO = new ApiResponse<>(profileService.updateProofOfAddress(proofOfAddressDTO, file, authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @CrossOrigin("http://localhost:5173")
    @GetMapping(value = "/getDetails")
    @Operation(summary = "Update proof of address information for a user profile")
    public ResponseEntity<ApiResponse<ContactInformation>> getContact(
            @Parameter(description = "Proof of address information to update", required = true)
            @RequestHeader("Authorization") String authorizationHeader) {

        ApiResponse<ContactInformation> responseDTO = new ApiResponse<>(profileService.getContact(authorizationHeader));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
