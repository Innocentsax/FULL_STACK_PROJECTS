package com.wakacast.services;

import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.models.CastCall;
import com.wakacast.models.Role;
import org.springframework.data.domain.Page;


public interface RoleSearchService {
    Page<CastCall> getCastCallMatchingRoles(CastCallPage castCallPage, RoleMatchSearchCriteria roleMatchSearchCriteria);
    Page<CastCall> getSuggestedCastCallsBasedOnUserProfile();
    Page<Role> getAllRoles();
    Role getRoleById(Long roleId);
    Page<UserResponseDto> getUsersByRole(UserRoleSearchDTO userRoleSearchDTO);
}
