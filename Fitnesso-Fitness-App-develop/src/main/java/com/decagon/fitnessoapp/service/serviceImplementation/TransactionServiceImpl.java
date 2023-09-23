package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.transactionDto.TransactionRequestDTO;
import com.decagon.fitnessoapp.dto.transactionDto.TransactionResponseDTO;
import com.decagon.fitnessoapp.dto.transactionDto.response.PaymentResponse;
import com.decagon.fitnessoapp.dto.transactionDto.response.TransVerificationResponse;
import com.decagon.fitnessoapp.model.product.CheckOut;
import com.decagon.fitnessoapp.model.product.ORDER_STATUS;
import com.decagon.fitnessoapp.model.product.Order;
import com.decagon.fitnessoapp.model.product.TRANSACTION_STATUS;
import com.decagon.fitnessoapp.repository.CheckOutRepository;
import com.decagon.fitnessoapp.repository.OrderRepository;
import com.decagon.fitnessoapp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final CheckOutRepository checkOutRepository;
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;

    @Value("${website.redirect}")
    private String redirectWebsite;
    @Value("${api.payStack}")
    private String payStackKey;
    @Value("${server.port}")
    private Integer port;

    @Autowired
    public TransactionServiceImpl(CheckOutRepository checkOutRepository, RestTemplate restTemplate, OrderRepository orderRepository) {
        this.checkOutRepository = checkOutRepository;
        this.restTemplate = restTemplate;
        this.orderRepository = orderRepository;
    }

    @Override
    public TransactionResponseDTO initializeTransaction(String email, BigDecimal totalPrice,
                                                        String referenceNumber, Long cardNumber){

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
//        final String successfulTransaction = "http://"+ redirectWebsite + "/transaction/success";
        final String successfulTransaction = "http://"+ redirectWebsite;
//        final String failedTransaction = "http://" + redirectWebsite + "/transaction/fail";
        final String failedTransaction = "http://" + redirectWebsite;
        final String url = "https://api.paystack.co/transaction/initialize";
        final String key = payStackKey;
        log.info(totalPrice.toString());

        transactionRequestDTO.setEmail(email);
        transactionRequestDTO.setTransaction_charge(1);
        transactionRequestDTO.setAmount(totalPrice.toString());
        transactionRequestDTO.setReference(referenceNumber);
        transactionRequestDTO.setCallback_url(successfulTransaction);
        transactionRequestDTO.setMetadata(Map.of("cancel_action", failedTransaction,
                "card_number", cardNumber));
        transactionRequestDTO.setChannels(List.of("CARD"));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + key);
        HttpEntity<TransactionRequestDTO> entity =
                new HttpEntity<>(transactionRequestDTO, headers);

        ResponseEntity<TransactionResponseDTO> response =
                restTemplate.postForEntity(url, entity,TransactionResponseDTO.class);
        return response.getBody();
    }


    @Override
    public PaymentResponse confirmation(String reference) {
        final String url = "https://api.paystack.co/transaction/verify/" + reference;
        HttpHeaders headers = new HttpHeaders();
        String key = "API.API_KEY_PAYMENT";
        headers.set("Authorization", "Bearer " + key);
        HttpEntity<TransVerificationResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<TransVerificationResponse> response = restTemplate
                .exchange(url, HttpMethod.GET,entity,TransVerificationResponse.class);
        return printStatus(Objects.requireNonNull(response.getBody()), reference);
    }


    public PaymentResponse printStatus(TransVerificationResponse transaction, String referenceNumber){
        if(transaction.getData().getStatus().equals("success")){
            CheckOut checkOut = checkOutRepository.findByReferenceNumber(referenceNumber).orElse(null);
            if(checkOut != null) {
                checkOut.setTransactionStatus(TRANSACTION_STATUS.COMPLETED);
                checkOutRepository.save(checkOut);
                Order order = new Order();
                order.setCheckOut(checkOut);
                order.setOrderStatus(ORDER_STATUS.PENDING);
                orderRepository.save(order);
                return PaymentResponse.builder().message("Payment was Successful").status("OK").build();
            }
            return PaymentResponse.builder().message("Unable to find the check out").status("ERROR").build();
        }
        return PaymentResponse.builder().message("Transaction Failed").build();
    }

}
