package com.wakacast.controllers;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.search_criteria.CastCallSearchCriteria;
import com.wakacast.services.CastCallSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CastCallSearchController {
    private final CastCallSearchService castCallSearchService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/search-cast-call")
    public ResponseEntity<Page<CastCallDto>> getCastCallSearch(CastCallSearchCriteria castCallSearchCriteria, CastCallPage castCallPage) {
        System.out.println(castCallSearchCriteria.getProjectName());
        log.info("some {}", castCallSearchCriteria.getProjectName());
        return new ResponseEntity<>(castCallSearchService.getCastCallSearch(castCallPage, castCallSearchCriteria),  HttpStatus.OK);
    }
}
