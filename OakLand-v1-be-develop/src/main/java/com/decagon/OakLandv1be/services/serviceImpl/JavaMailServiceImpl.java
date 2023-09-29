package com.decagon.OakLandv1be.services.serviceImpl;

import com.decagon.OakLandv1be.OakLandV1BeApplication;
import com.decagon.OakLandv1be.services.JavaMailService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class JavaMailServiceImpl implements JavaMailService {
    private final JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(OakLandV1BeApplication.class);
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");
    @Override

    public ResponseEntity<String> sendMail(String receiverEmail, String subject, String text) throws IOException {

        if (!isValidEmail(receiverEmail))
            new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);

        isEmailDomainValid(receiverEmail);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("funitureoakland@gmail.com");
        message.setTo(receiverEmail);
        message.setSentDate(new Date());
        message.setSubject(subject);
        message.setText(text);


        try {
            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT, "Sending mail to: " + receiverEmail);
            javaMailSender.send(message);
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(IMPORTANT, e.getMessage());
        }

        return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }
    public void isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.lastIndexOf('@') + 1);
            int result = doLookup(domain);
            LOGGER.info("Domain: " + domain);
            LOGGER.info("Result of domain: " + result);
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public int doLookup(String hostName) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        if( attr == null ) return( 0 );
        return( attr.size() );
    }


    public ResponseEntity<String> sendMailAlt(String receiverEmail, String subject, String text) {

        if (!isValidEmail(receiverEmail))
        new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);

        isEmailDomainValid(receiverEmail);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom("funitureoakland@gmail.com");
            message.setTo(receiverEmail);
            message.setSubject(subject);
            message.setText(text, true);
//            message.addInline("logo", new ClassPathResource("img/logo.gif"));
//            message.addAttachment("myDocument.pdf", new ClassPathResource("uploads/document.pdf"));
        };

        try {
            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT, "Sending mail to: " + receiverEmail);
            javaMailSender.send(messagePreparator);
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(IMPORTANT, e.getMessage());
        }

        return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

