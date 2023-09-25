package com.decadev.money.way.controller;

import com.decadev.money.way.service.PDFExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000/")
@RequestMapping("/api/v1/transactions")
public class PDFExportController {
    private final PDFExportService exportService;

    @GetMapping("/export-pdf/{transactionID}")
    public ResponseEntity<byte[]> exportPDF(@PathVariable(value = "transactionID")Long transactionID) throws IOException {
        return exportService.exportPDF(transactionID);
    }

}
