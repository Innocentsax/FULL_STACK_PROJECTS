package com.decadev.money.way.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PDFExportService {


    ResponseEntity<byte[]> exportPDF(Long transactionID) throws IOException;
}
