package com.wakacast.services;

import com.wakacast.dto.*;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.io.IOException;

public interface UserServices {
    String signup(SignUpDto signUpDto) throws IOException;
    void verifyAccount(String token);
    UserDto getLoggedInUser();
    String setUpProfile(ProfileSetupDto setupDto);
    String uploadProfilePic(MultipartFile profilePic) throws IOException;
    String uploadPortfolio(MultipartFile portfolioFile, PortfolioDto portfolioDto) throws IOException;
    String postEquipmentForLease(EquipmentRequestedDto equipmentRequestedDto);
    String uploadEquipmentImage(long equipmentId, MultipartFile image) throws IOException;
    String editPostedEquipment(long equipmentId, String equipmentName);
    String deletePostedEquipment(long equipmentId);
    String deleteUserAccount();
    String logout(LogOutRequestDto logOutRequest);
    String setUpSearchAgent(RoleMatchSearchCriteria roleMatchSearchCriteria);
    String editSearchAgent(long searchAgentId, RoleMatchSearchCriteria roleMatchSearchCriteria);
    String removeSearchAgent(long searchAgentId);
    String addSuperAdmin(SignUpDto signUpDto) throws IOException;
    String addRoleToUser(RoleUserDto roleUserDto) throws RoleNotFoundException;
    UserDto getUserById(Long id);
}
