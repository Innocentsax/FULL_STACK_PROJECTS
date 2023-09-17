package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.configuration.paystackconfiguration.InitializeTransaction;
import com.decagon.safariwebstore.configuration.paystackconfiguration.InitializeTransactionRequest;
import com.decagon.safariwebstore.configuration.paystackconfiguration.InitializeTransactionResponse;
import com.decagon.safariwebstore.configuration.paystackconfiguration.PayStackVerifyTransaction;
import com.decagon.safariwebstore.dto.OrderResponseDTO;
import com.decagon.safariwebstore.model.Payment;
import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.repository.PaymentRepository;
import com.decagon.safariwebstore.service.OrderService;
import com.decagon.safariwebstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderService orderService;

    public String getPaymentAuthorizationUrl(Long orderId) throws Exception {



        OrderResponseDTO order = orderService.getOrder(orderId);

        InitializeTransactionRequest request = new InitializeTransactionRequest();
        request.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        //converting to Kobo
        request.setAmount((int) (order.getTotalCost() * 100));

      // request.setCallback_url("http://localhost:8045/api/payment/confirm/" + orderId);
       request.setCallback_url("https://safariwebstoreapp.herokuapp.com/api/payment/confirm/" + orderId);

        InitializeTransactionResponse response = InitializeTransaction.initTransaction(request);
        String authorizationUrl = response.getData().getAuthorization_url();

        if(!response.getStatus()){
            return null;
        }

        try {
            //save it in the database to check later if user has made payment;
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setConfirmPayment(false);
            payment.setPaymentReference(response.getData().getReference());
            paymentRepository.save(payment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return authorizationUrl;
    }



    public ResponseEntity<Response> confirmPayment(Long orderId) throws Exception {

        Response response = new Response();

        Optional<Payment> paymentOptional = paymentRepository.findPaymentByOrderId(orderId);

        if(paymentOptional.isEmpty()) {
            return new ResponseEntity<>(
                    new Response(404,"Particular order Not Found"),
                    HttpStatus.NOT_FOUND);
        }

        //payment reference
        String paymentReference = paymentOptional.get().getPaymentReference();

        PayStackVerifyTransaction payStackVerifyTransaction = new PayStackVerifyTransaction();
        payStackVerifyTransaction = payStackVerifyTransaction.verifyTransaction(paymentReference);

        if(payStackVerifyTransaction == null){
            paymentOptional.get().setConfirmPayment(true);
            paymentRepository.save(paymentOptional.get());

            response.setStatus(201);
            response.setMessage("Payment already made on this order");

        }else{

            if (!payStackVerifyTransaction.getData().getStatus().equals("success")){
                response.setStatus(200);
                response.setMessage("Payment has not been made yet on this order");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
