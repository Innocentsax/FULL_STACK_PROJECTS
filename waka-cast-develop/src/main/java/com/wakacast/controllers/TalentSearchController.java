package com.wakacast.controllers;

import com.wakacast.dto.pages_criteria.TalentPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.dto.search_criteria.TalentSearchCriteria;
import com.wakacast.services.TalentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TalentSearchController {

    private final TalentSearchService talentSearchService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search-talent-filter")
    public ResponseEntity<Page<UserResponseDto>> getTalentsByFilter(TalentSearchCriteria talentSearchCriteria) {
        return new ResponseEntity<>(talentSearchService.getTalentsWithFilter(talentSearchCriteria),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get-all-talents")
    public ResponseEntity<Page<UserResponseDto>> getAllTalents(TalentPage talentPage) {
        return new ResponseEntity<>(talentSearchService.getAllTalents(talentPage),  HttpStatus.OK);
    }
}
