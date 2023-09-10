package com.goCash.controller;
import com.goCash.services.DataService;
import com.goCash.utils.ApiResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/goCash")

public class DataController {
    private final DataService dataService;

    @GetMapping("/{provider}")
    public ResponseEntity<?> getDataVariation(@PathVariable String provider) {
        log.info("request to view different data packages like MTN, AIRTEL, GLO and ETISALAT");
        ApiResponse response = dataService.getDataVariation(provider);
        return ResponseEntity.ok(response);
    }
}
