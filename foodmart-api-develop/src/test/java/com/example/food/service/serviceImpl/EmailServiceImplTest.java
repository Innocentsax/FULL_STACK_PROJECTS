package com.example.food.service.serviceImpl;

import com.example.food.dto.EmailSenderDto;
import com.example.food.services.serviceImpl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {
    @Mock
    private JavaMailSender emailSender;

    @Mock
    private MimeMessage mimeMessage;
    @InjectMocks
    private EmailServiceImpl emailService;
    @Test
    public void testSendMail() throws MessagingException {
        EmailSenderDto emailSenderDto = new EmailSenderDto();
        emailSenderDto.setTo("test@example.com");
        emailSenderDto.setSubject("Test Subject");
        emailSenderDto.setContent("Test Content");
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);
        emailService.sendMail(emailSenderDto);
        verify(emailSender).send(mimeMessage);
    }
}