package com.decagon.safariwebstore.configuration.paystackconfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

class PayStackVerifyTransactionTest {
    private PayStackVerifyTransaction payStackVerifyTransaction = new PayStackVerifyTransaction();
    private InitializeTransactionResponse response = new InitializeTransactionResponse();
    private InitializeTransactionRequest request = new InitializeTransactionRequest();
    @BeforeEach
    void setup() throws Exception {
        request.setAmount(300 * 100);
        request.setEmail("adenusidamilola5@gmail.com");
        response = InitializeTransaction.initTransaction(request);
        payStackVerifyTransaction = payStackVerifyTransaction.verifyTransaction(response.getData().getReference());
        System.out.println(payStackVerifyTransaction.getData());
    }
    @Test
    void verifyURLWasSent(){
        assertEquals("Verification successful", payStackVerifyTransaction.getMessage());
        assertNotNull(payStackVerifyTransaction.getData().getTransaction_date());
    }
    @Test
    void verifyPaymentHasNotBeenMade(){
        assertEquals("abandoned", payStackVerifyTransaction.getData().getStatus());
        assertEquals("The transaction was not completed", payStackVerifyTransaction.getData().getGateway_response());
        assertNull(payStackVerifyTransaction.getData().getPaid_at());
    }
    @Test
    void randomRefKeyShouldThrowExceptions() throws Exception {
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                PayStackVerifyTransaction payStackVerifyTransaction = new PayStackVerifyTransaction();
                payStackVerifyTransaction = payStackVerifyTransaction.verifyTransaction("ysttg543sha");
                System.out.println((payStackVerifyTransaction));
            }
        });
    }
}