package com.decagon.fitnessoapp.Email;



//import com.mailjet.client.errors.MailjetException;
//import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.mockito.Mockito.*;

public class EmailServiceImplTestCase {


    @Test
//    @Disabled
    public void sendMessage(){
        String email ="chika@decagon.dev";
        String text ="how are you doing?";
        String subject ="Greetings from team fitnesso";
        EmailServiceImpl emailService;
        emailService = mock(EmailServiceImpl.class);
        doNothing().when(emailService).sendMessage(subject,email,text);
        emailService.sendMessage(subject,email,text);
        verify(emailService,times(1)).sendMessage(subject,email,text);

    }
}
