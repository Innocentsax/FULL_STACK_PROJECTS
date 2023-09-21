package com.decagon.eventhubbe.service;

import com.decagon.eventhubbe.dto.request.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {
    String makePayment(PaymentRequest paymentDTO,String id);
}
