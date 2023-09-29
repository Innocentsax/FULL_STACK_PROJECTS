package com.decagon.OakLandv1be;


import com.decagon.OakLandv1be.services.serviceImpl.JavaMailServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class JavaMailServiceImplTest {
    @Mock
    JavaMailServiceImpl javaMailServiceImpl;

    @Test
    void sendEmail() throws IOException {
        Mockito.when(javaMailServiceImpl.sendMail(anyString(), anyString(), anyString()))
                .thenReturn(new ResponseEntity<>("sent", HttpStatus.OK));

        ResponseEntity<String> responseEntity =
                javaMailServiceImpl.sendMail("ilemonamustapha@gmail.com", "A random subject", "A random body");

        Assertions.assertThat(responseEntity.getBody().equals("sent"));
        Assertions.assertThat(responseEntity.getStatusCode().equals(HttpStatus.OK));
    }

}
