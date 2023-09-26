package com.example.money_way.service;

import com.lowagie.text.Document;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PdfGeneratorService {

    ResponseEntity<Document> export(Long transactionId, HttpServletResponse response) throws IOException;


}
