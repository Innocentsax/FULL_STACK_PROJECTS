package com.decadev.money.way.service.impl;

import com.decadev.money.way.exception.TransactionException;
import com.decadev.money.way.model.Transaction;
import com.decadev.money.way.model.User;
import com.decadev.money.way.repository.TransactionRepository;
import com.decadev.money.way.service.PDFExportService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PDFExportServiceImpl implements PDFExportService {
    private final TransactionRepository transactionRepository;


    public ResponseEntity<byte[]> exportPDF(Long transactionID) throws IOException, DocumentException {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userID = user.getId();

        Transaction transaction = transactionRepository.findTransactionByIdAndUserId(transactionID,userID)
                .orElseThrow(() -> new TransactionException("Transaction Not Found"));

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String currentDate = dateFormat.format(new Date());

        Document document = new Document(PageSize.A4);

        // Create a ByteArrayOutputStream to hold the generated PDF content
        ByteArrayOutputStream contentHolder = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, contentHolder);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
        font.setColor(Color.black);
        font.setSize(25);
        Paragraph paragraph1 = new Paragraph("Transaction Receipt", font);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph1.setFont(FontFactory.getFont(FontFactory.COURIER_BOLD, 30, Color.black));

        Paragraph paragraph2 = new Paragraph();
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph2.setFont(FontFactory.getFont(FontFactory.COURIER, 16, Color.black));
        paragraph2.setMultipliedLeading(2);
        paragraph2.add(
                "Amount: " + transaction.getAmount() + "\n" +
                        "Status: " + transaction.getStatus() + "\n" +
                        "Type: " + transaction.getCashTransferType() + "\n" +
                        "Beneficiary: " + transaction.getBeneficiary() + "\n" +
                        "Date: " + transaction.getDate() + "\n" +
                        "Description: " + transaction.getDescription() + "\n" +
                        "ReferenceID: " + transaction.getReferenceId() + "\n"
        );

        document.add(paragraph1);
        document.add(paragraph2);
        document.close();

        // Retrieve the generated PDF content from the ByteArrayOutputStream
        byte[] pdfContent = contentHolder.toByteArray();

        // Set the appropriate response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
       headers.setContentDispositionFormData("attachment", "transaction_receipt.pdf");

        // Return the PDF content as a ResponseEntity
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
    }


}
