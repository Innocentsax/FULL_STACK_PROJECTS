package com.wakacast.controllers;

import com.wakacast.dto.UserRoleSearchDTO;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.models.CastCall;
import com.wakacast.models.Role;
import com.wakacast.models.User;
import com.wakacast.services.RoleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class RoleSearchController {

    private final RoleSearchService roleSearchService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search-matching-roles")
    public ResponseEntity<Page<CastCall>> getAllMatchingRoles(CastCallPage castCallPage, RoleMatchSearchCriteria roleMatchSearchCriteria) {
        return new ResponseEntity<>(roleSearchService.getCastCallMatchingRoles(castCallPage, roleMatchSearchCriteria),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/suggested-cast-calls")
    public ResponseEntity<Page<CastCall>> getSuggestedCastCalls() {
        return new ResponseEntity<>(roleSearchService.getSuggestedCastCallsBasedOnUserProfile(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-roles")
    public ResponseEntity<Page<Role>> getAllRoles() {
        return new ResponseEntity<>(roleSearchService.getAllRoles(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-role-by-id/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable ("id") Long id) {
        return new ResponseEntity<>(roleSearchService.getRoleById(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-users-by-roles")
    public ResponseEntity<Page<UserResponseDto>> getUsersByRole (UserRoleSearchDTO userRoleSearchDTO) {
        return new ResponseEntity<>(roleSearchService.getUsersByRole(userRoleSearchDTO), HttpStatus.OK);
    }
}
