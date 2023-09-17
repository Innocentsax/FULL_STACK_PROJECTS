package com.decagon.safariwebstore.configuration.paystackconfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class InitializeTransactionTest {
    private final InitializeTransactionRequest request = new InitializeTransactionRequest();
    private InitializeTransactionResponse response = new InitializeTransactionResponse();
    @BeforeEach
    void setup() throws Exception {
        request.setAmount(300*100);
        request.setEmail("adenusidamilola5@gmail.com");
        response = InitializeTransaction.initTransaction(request);
    }
    @Test
    void authorizationURLShouldBeCreated(){
        String message = response.getMessage();
        assertEquals("Authorization URL created",message);
        assertNotNull(message);
    }
    @Test
    void shouldReturnAuthorizationURL(){
        String authorizationUrl = response.getData().getAuthorization_url();
        assertNotNull(authorizationUrl);
        assertEquals(0, authorizationUrl.indexOf("https://checkout.paystack.com/"));
        assertTrue(response.getStatus());
    }
    @Test
    void shouldReturnPaymentReference(){
        String reference = response.getData().getReference();
        assertNotNull(reference);
        assertNotNull(response.getData().getAccess_code());
    }
    @Test
    void shouldReturnPaymentAccessCode(){
        String accessCode = response.getData().getAccess_code();
        assertNotNull(accessCode);
    }
}
