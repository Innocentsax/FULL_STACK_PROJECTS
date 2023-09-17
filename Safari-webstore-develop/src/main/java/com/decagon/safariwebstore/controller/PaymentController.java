package com.decagon.safariwebstore.controller;

import com.decagon.safariwebstore.payload.response.Response;
import com.decagon.safariwebstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/pay/{id}")
    public String makePayment(HttpServletResponse httpServletResponse, @PathVariable String id) throws Exception {
        Long orderId = Long.valueOf(id);
        String paymentUrl = paymentService.getPaymentAuthorizationUrl(orderId);

        return paymentUrl;
    }


    @GetMapping(value = "/confirm/{order}")
    @Secured({"ADMIN","USER"})
    public void confirmPayment(@PathVariable String order, HttpServletResponse response) throws Exception {
        Long orderId = Long.valueOf(order);

        paymentService.confirmPayment(orderId);

        //response.sendRedirect("http://localhost:3000/success-payment/");
        response.sendRedirect("https://safari-web-store.web.app/success-payment/");

    }

}