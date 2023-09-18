package com.example.decapay.configurations.mails;

import com.example.decapay.pojos.mailDto.MailDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.any;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {
    @Mock
    EmailSenderServiceImpl emailSenderServiceImpl;


    @Test
    void sendEmail() {
        MailDto mailDto = new MailDto("peterhamza6@gmail.com", "this is a testing mail", "testing");
        Mockito.when(emailSenderServiceImpl.sendEmail(any())).thenReturn(new ResponseEntity<>("Message sent successfully", HttpStatus.OK));

        ResponseEntity<String> responseEntity = emailSenderServiceImpl.sendEmail(mailDto);

        Assertions.assertThat(responseEntity.getBody()).isEqualTo("Message sent successfully");
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


    }
}