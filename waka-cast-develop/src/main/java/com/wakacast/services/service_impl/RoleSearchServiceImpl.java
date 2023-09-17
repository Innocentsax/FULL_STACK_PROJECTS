package com.wakacast.services.service_impl;

import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.CastCall;
import com.wakacast.models.Role;
import com.wakacast.models.SearchAgent;
import com.wakacast.models.User;
import com.wakacast.repositories.CastCallRepository;
import com.wakacast.repositories.RoleRepository;
import com.wakacast.repositories.SearchAgentRepository;
import com.wakacast.repositories.UserRepository;
import com.wakacast.repositories.criteri_class.CastCallSearchCriteriaRepository;
import com.wakacast.repositories.criteri_class.UserSearchCriteriaRepository;
import com.wakacast.services.RoleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleSearchServiceImpl implements RoleSearchService {

    private final CastCallSearchCriteriaRepository castCallSearchCriteriaRepository;
    private final UserRepository userRepository;
    private final CastCallRepository castCallRepository;
    private final SearchAgentRepository searchAgentRepository;
    private final RoleRepository roleRepository;
    private final UserSearchCriteriaRepository userSearchCriteriaRepository;

    @Override
    public Page<CastCall> getCastCallMatchingRoles(CastCallPage castCallPage, RoleMatchSearchCriteria roleMatchSearchCriteria) {
        return castCallSearchCriteriaRepository.findMatchingRoles(castCallPage,roleMatchSearchCriteria);
    }

    @Override
    public Page<CastCall> getSuggestedCastCallsBasedOnUserProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound("User not found"));

        List<CastCall> castCallList = new ArrayList<>();
        boolean status = user.getUserPersonas().stream()
                .anyMatch(persona -> persona.getPersona().equalsIgnoreCase("Talent"));

        if (status) {
            user.getRoles().stream()
                    .map(role -> castCallRepository.findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndIsReportedEnough
                    (LocalDateTime.now().minusHours(12), role.getRoleTitle(), false))
                    .forEach(castCalls -> {
                castCalls.sort(Comparator.comparing(CastCall::getCreateDate).reversed());
                castCallList.addAll(castCalls);
            });
        }
        return new PageImpl<>(castCallList);
    }

    @Override
    public Page<Role> getAllRoles() {
        List<Role> roleList = roleRepository.findAll();
        return new PageImpl<>(roleList);
    }

    @Override
    public Role getRoleById(Long roleId) {
        Optional<Role> returnedRole = roleRepository.findRoleById(roleId);
        if(returnedRole.isPresent())
            return returnedRole.get();
        throw new ResourceNotFound("Role does not exist");
    }

    @Override
    public Page<UserResponseDto> getUsersByRole(UserRoleSearchDTO userRoleSearchDTO) {;
        return userSearchCriteriaRepository.findUserByRole(userRoleSearchDTO);
    }


    private void emailMatchingRoles() throws IOException {
        List<SearchAgent> searchAgents = searchAgentRepository.findAll();
        for (SearchAgent searchAgent : searchAgents) {
            List<CastCall> matchingCastCalls = castCallRepository
                    .findCastCallsByCreateDateIsAfterAndTalentSkillContainingAndStateContainingAndIsReportedEnough(
                            LocalDate.now().minusDays(1).atStartOfDay(), searchAgent.getTalentSkill(),
                                    searchAgent.getLocation(), false
                    );

/*
            if (!matchingCastCalls.isEmpty()) {
                User user = searchAgent.getUser();
                EmailRequest emailRequest = new EmailRequest();
                emailRequest.setTo(user.getEmail());
                emailRequest.setSubject("New Roles Available for You");
                emailRequest.setBody(matchingCastCalls.size() + " new roles available for you \n" +
                        matchingCastCalls.get(0));
                emailService.sendEmail(emailRequest); todo
            }
*/
        }
    }

    @Scheduled(cron = "${cron.expression}")
    public void scheduleFixedDelayTask() throws IOException {
        emailMatchingRoles();
    }
}
