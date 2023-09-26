package com.example.money_way.service.impl;

import com.example.money_way.exception.ResourceNotFoundException;
import com.example.money_way.model.Transaction;
import com.example.money_way.model.User;
import com.example.money_way.repository.TransactionRepository;
import com.example.money_way.service.PdfGeneratorService;
import com.example.money_way.utils.AppUtil;
import com.lowagie.text.Font;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PdfGeneratorServiceImplementation implements PdfGeneratorService {
    private final AppUtil appUtil;
    private final TransactionRepository transactionRepository;
    @Override
    public ResponseEntity<Document> export(Long transactionId, HttpServletResponse response) throws IOException {

        User user = appUtil.getLoggedInUser();
        Long userId = user.getId();
        String sender = user.getFirstName() + " " + user.getLastName();

        Optional<Transaction> transaction1 = transactionRepository.findByUserIdAndTransactionId(userId, transactionId);
        if (transaction1.isEmpty()) {
            throw new ResourceNotFoundException("Transaction not found");
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
        font.setColor(Color.BLACK);
        font.setSize(25);
        Paragraph p1 = new Paragraph("Transaction Receipt", font);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph p2 = new Paragraph();
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setFont(FontFactory.getFont(FontFactory.COURIER, 16, Color.black));
        p2.setMultipliedLeading(2);
        p2.add  ("Name: " + sender + '\n' +
                 "Amount: " + transaction1.get().getAmount() + '\n' +
                 "Currency: " + transaction1.get().getCurrency() + '\n' +
                 "Description: " + transaction1.get().getDescription() + '\n' +
                 "Payment Type: " + transaction1.get().getPaymentType() + '\n' +
                 "Virtual Account Ref: " + transaction1.get().getVirtualAccountRef() + '\n' +
                 "Request-Id: " + transaction1.get().getRequestId() + '\n' +
                 "Transaction Date: " + transaction1.get().getCreatedAt() + '\n' +
                 "Provider Status: " + transaction1.get().getProviderStatus() + '\n' +
                 "Response Message: " + transaction1.get().getResponseMessage() + '\n' +
                 "Current Date: " + currentDateTime);

        document.add(p1);
        document.add(p2);
        document.close();

        return ResponseEntity.ok(document);
    }

}
