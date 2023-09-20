package com.decagon.service.serviceImplementation;

import com.decagon.config.CloudinaryConfig;
import com.decagon.domain.constant.ProfileStatus;
import com.decagon.domain.entity.Profile;
import com.decagon.domain.message.UserObject;
import com.decagon.domain.screen.*;
import com.decagon.dto.pojoDTO.*;
import com.decagon.dto.response.ProfileResponseDTO;
import com.decagon.exception.InvalidTokenException;
import com.decagon.exception.ProfileNotFoundException;
import com.decagon.repository.ProfileRepository;
import com.decagon.service.ProfileService;
import com.decagon.service.UploadService;
import com.decagon.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    private final JwtUtils jwtUtils;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public ProfileResponseDTO getProfile(String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));

        return new ProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO createProfile(String user_id, ContactInformationDTO contactInformationDTO) throws JsonProcessingException {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setFirstName(contactInformationDTO.getFirstName());
        contactInformation.setLastName(contactInformationDTO.getLastName());
        contactInformation.setEmail(contactInformationDTO.getEmail());

        Profile profile = new Profile();
        profile.setUserId(user_id);
        profile.setStatus(ProfileStatus.NEW);
        profile.setContactInformation(mapper.writeValueAsString(contactInformation));

        profile = profileRepository.save(profile);

        return new ProfileResponseDTO(profile);
    }

    @Override
    public ContactInformation getContact(String authorizationHeader){
        String userId = getUserID(authorizationHeader);
        if(userId!=null){
            Profile profile = profileRepository.findByUserId(userId)
                    .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));

           String contact = profile.getContactInformation();
           ContactInformation contactInformation = new Gson().fromJson(contact,ContactInformation.class);
           return contactInformation;

        }
        return null;

    }

    @Override
    public String updateContactInformation(ContactInformationDTO contactInformationDTO, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));

        ContactInformation contactInformation = new ContactInformation(contactInformationDTO);
        if (StringUtils.isBlank(profile.getContactInformation())) {
            profile.setStatus(ProfileStatus.CONTACT_UPDATED);
        }
        try {
            profile.setContactInformation(mapper.writeValueAsString(contactInformation));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        if(Objects.isNull(profile.getContactInformation())) {
            profile.setStatus(ProfileStatus.CONTACT_UPDATED);
        }
        profile=profileRepository.save(profile);
        new ProfileResponseDTO(profile);
        return "success";

    }

    @Override
    public ProfileResponseDTO updateEmploymentStatus(EmploymentStatusDTO employmentStatusDTO, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        EmploymentStatus employmentStatus = new EmploymentStatus(employmentStatusDTO);
        if (StringUtils.isBlank(profile.getEmploymentStatus())) {
            profile.setStatus(ProfileStatus.EMPLOYMENT_UPDATED);
        }
        try {
            profile.setEmploymentStatus(mapper.writeValueAsString(employmentStatus));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        profile = profileRepository.save(profile);
        return new ProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO updateGovernmentID(GovernmentIDDTO governmentIDDTO, MultipartFile file, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        String url = uploadFile(file, profile.getId());
        GovernmentID governmentID = new GovernmentID(governmentIDDTO);
        governmentID.setDocumentUrl(url);
        if (StringUtils.isBlank(profile.getGovernmentId())) {
            profile.setStatus(ProfileStatus.GOVERNMENT_UPDATED);
        }
        try {
            profile.setGovernmentId(mapper.writeValueAsString(governmentID));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        profile = profileRepository.save(profile);
        return new ProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO updateIncomeStatus(IncomeStatusDTO incomeStatusDTO, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        IncomeStatus incomeStatus = new IncomeStatus(incomeStatusDTO);
        if (Objects.isNull(profile.getIncomeStatus())) {
            profile.setStatus(ProfileStatus.INCOME_UPDATED);
        }
        try {
            profile.setIncomeStatus(mapper.writeValueAsString(incomeStatus));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        profile = profileRepository.save(profile);
        return new ProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO updateBankAccount(BankAccountDTO bankAccountDTO, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        BankAccount bankAccount = new BankAccount(bankAccountDTO);
        if (StringUtils.isBlank(profile.getBankAccount())) {
            profile.setStatus(ProfileStatus.BANK_ACCOUNT_UPDATED);
        }
        try {
            profile.setBankAccount(mapper.writeValueAsString(bankAccount));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        profile = profileRepository.save(profile);
        return new ProfileResponseDTO(profile);
    }

    @Override
    public ProfileResponseDTO updateProofOfAddress(ProofOfAddressDTO proofOfAddressDTO, MultipartFile file, String authorizationHeader) {
        String userId = getUserID(authorizationHeader);
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        String url = uploadFile(file, profile.getId());
        ProofOfAddress proofOfAddress = new ProofOfAddress(proofOfAddressDTO);
        proofOfAddress.setDocument_Url(url);
        if (StringUtils.isBlank(profile.getProofOfAddress())) {
            profile.setStatus(ProfileStatus.PROOF_OF_ADDRESS);
        }
        try {
            profile.setProofOfAddress(mapper.writeValueAsString(proofOfAddress));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        profile = profileRepository.save(profile);
        return new ProfileResponseDTO(profile);
    }

    private String uploadFile(MultipartFile file, Long id) {
        String generator = UUID.randomUUID().toString() + id;
        CloudinaryConfig cloudinaryConfig =new CloudinaryConfig();
        return cloudinaryConfig.imageLink(file, generator);
    }

    public String getUserID(String authorizationHeader) {
        String token = jwtUtils.extractToken(authorizationHeader);
        String userId = jwtUtils.extractUserIdFromToken(token);

        if (userId == null) {
            throw new InvalidTokenException("UserId is null");
        }
        return userId;
    }
}