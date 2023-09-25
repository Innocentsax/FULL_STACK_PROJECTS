package com.decadev.money.way.event;

import com.decadev.money.way.model.Transaction;
import com.decadev.money.way.model.User;
import com.decadev.money.way.service.UserService;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventListener implements ApplicationListener<UserRegistrationEvent> {
    private final UserService userService;

    @Value("${mailgun.api-key}")
    private String API_KEY;
    @Value("${mailgun.domain}")
    private String YOUR_DOMAIN_NAME;

    private User user;
    private String imageurl="https://res.cloudinary.com/dafxzu462/image/upload/v1687697811/Frame_8402Logo_-_MoneyWay_an1xnp.svg";
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        user = event.getUser();
        String verificationToken = UUID.randomUUID().toString().substring(0, 10);
        userService.saveUserVerificationToken(user, verificationToken);
        String url = event.getRegistrationConfirmationUrl()+"/api/v1/auth/verify-email?token="+verificationToken;
       sendRegistrationVerificationEmail(url);
        log.info("Click to verify your account>>>>>>>>>>>>         "+url);
    }

    private JsonNode sendRegistrationVerificationEmail(String url){
        String content= "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "@import url('https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap');\n"+
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        \n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 30px;\n" +
                "            background-color: #D3D3D3;\n" +
                "        }\n" +
                "        \n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .image{\n" +
                "            width: 30px;\n" +
                "            margin-right: 10px;\n" +
                "            height: 30px;\n" +
                "}\n"+
                "        \n" +
                "        .button {\n" +
                "            display: block;\n" +
                "            width: 200px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "            background-color: #000080;\n" +
                "            color: #FFFFFF;\n" +
                "            text-decoration: none;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 20px;\n" +
                "        }\n" +
                "        .button:hover {" +
                "         background-color: #0000FF;" +
                "         color: #FFFFFF;  " +
                "}     "+
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
//                "            <img src="+imageurl+"class=\"image\" alt=\"Logo\">\n" +
                "<h1>MoneyWay</h1>"+
                "        </div>\n" +
                "        <p>Dear "+user.getFirstName()+",</p>\n" +
                "        <p>Thank you for signing up with Money Way. We're thrilled to have you on board and can't wait to unlock the full potential together.\n" +
                "\n" +
                "To complete the registration process and ensure the security of your account, please verify your email address by clicking the verification link below:</p>\n" +
                "        <a class=\"button\" href="+url+">Verify email</a>\n" +
                "        <p>By verifying your email, you will gain full access to all the features and services offered by Money Way. Please note that the link will expire after 1 hour</p>" +
                "<p>If you didn't sign up with us, please disregard this email, and rest assured that your information remains secure.</p>\n" +
                "\n" +
                "<p>If you encounter any issues or need assistance, our dedicated support team is here to help. Simply reach out to our support team at support@moneyway.com for prompt assistance.</p>\n" +
                "\n" +
                "<p>Thank you for choosing Money Way. We look forward to providing you with a seamless and secure financial experience!.\n" +
                "\n" +
                "<p>Best regards,</p>\n" +
                "\n" +
                "<h2>The Money Way Team</h2>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";



        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .queryString("from", "MoneyWay noreply@moneyway.com")
                .queryString("to", user.getEmail())
                .queryString("subject", "Verify your email for Money Way")
                .queryString("html", content)
                .asJson();
        return request.getBody();
    }
    public JsonNode sendForgotPasswordVerificationEmail(String resetPasswordUrl, String email){
        String content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <style>\n" +
                "@import url('https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap');\n"+
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "        }\n" +
                "        \n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 30px;\n" +
                "            background-color: #D3D3D3;\n" +
                "        }\n" +
                "        \n" +
                "        .header {\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "        .image{\n" +
                "            width: 30px;\n" +
                "            margin-right: 10px;\n" +
                "            height: 30px;\n" +
                "}\n"+
                "        \n" +
                "        .button {\n" +
                "            display: block;\n" +
                "            width: 200px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 10px;\n" +
                "            text-align: center;\n" +
                "            background-color: #000080;\n" +
                "            color: #FFFFFF;\n" +
                "            text-decoration: none;\n" +
                "            font-weight: bold;\n" +
                "            border-radius: 20px;\n" +
                "        }\n" +
                "        .button:hover {\n" +
                "            background-color: #0000FF;\n" +
                "            color: #FFFFFF;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
//                "            <img src="+imageurl+"class=\"image\" alt=\"Logo\">\n" +
                "<h1>MoneyWay</h1>"+
                "        </div>\n" +
                "        <p>Dear user,</p>\n" +
                "        <p>We received a request to reset your password. To proceed with the password reset, please click the link below:</p>\n" +
                "        <a class=\"button\" href=" + resetPasswordUrl + ">Reset Password</a>\n" +
                "        <p>If you didn't request a password reset, please disregard this email. Your account is still secure.</p>\n" +
                "        <p>If you encounter any issues or need further assistance, please contact our support team at support@moneyway.com.</p>\n" +
                "        <p>Thank you for choosing Money Way.</p>\n" +
                "        <p>Best regards,</p>\n" +
                "        <p>The Money Way Team</p>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";


        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .queryString("from", "MoneyWay noreply@moneyway.com")
                .queryString("to", email)
                .queryString("subject", "Forgot Password")
                .queryString("html", content)
                .asJson();
        return request.getBody();
    }

    public JsonNode sendTransactionDetails(Transaction transaction){
       String content="<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <style>\n" +
               "@import url('https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap');\n"+
               "        body {\n" +
               "            margin: 0;\n" +
               "            padding: 0;\n" +
               "            font-family: Arial, sans-serif;\n" +
               "        }\n" +
               "        \n" +
               "        .container {\n" +
               "            max-width: 600px;\n" +
               "            margin: 0 auto;\n" +
               "            padding: 30px;\n" +
               "            background-color: #D3D3D3;\n" +
               "        }\n" +
               "        \n" +
               "        .header {\n" +
               "            justify-content: center;\n" +
               "            margin-bottom: 20px;\n" +
               "            display: flex;\n" +
               "            flex-direction: row;\n" +
               "            align-items: center;\n" +
               "            font-family: \"Inter\", sans-serif;\n" +
               "            font-weight: 600;\n" +
               "            font-size: 16px;\n" +
               "            color: #3538cd;\n" +
               "        }\n" +
               "        \n" +
               "        .transaction-details {\n" +
               "            text-align: center;\n" +
               "            margin-bottom: 20px;\n" +
               "            font-size: 20px;\n" +
               "            font-weight: bold;\n" +
               "        }\n" +
               "        \n" +
               "        .transaction-amount {\n" +
               "            text-align: center;\n" +
               "            margin-bottom: 20px;\n" +
               "            font-size: 24px;\n" +
               "            font-weight: bold;\n" +
               "            color: #000080;\n" +
               "        }\n" +
               "        \n" +
               "        .button {\n" +
               "            display: block;\n" +
               "            width: 200px;\n" +
               "            margin: 0 auto;\n" +
               "            padding: 10px;\n" +
               "            text-align: center;\n" +
               "            background-color: #000080;\n" +
               "            color: #FFFFFF;\n" +
               "            text-decoration: none;\n" +
               "            font-weight: bold;\n" +
               "            border-radius: 20px;\n" +
               "        }\n" +
               "        .image{\n" +
               "            width: 30px;\n" +
               "            margin-right: 10px;\n" +
               "            height: 30px;\n" +
               "}\n"+

               "        .button:hover {\n" +
               "            background-color: #0000FF;\n" +
               "            color: #FFFFFF;\n" +
               "        }\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <div class=\"container\">\n" +
               "        <div class=\"header\">\n" +
//               "            <img src="+imageurl+"class=\"image\" alt=\"Logo\">\n" +
               "<h1>MoneyWay</h1>"+
               "        </div>\n" +
               "        <p>Dear "+transaction.getUser().getFirstName()+",</p>\n" +
               "        <div class=\"transaction-details\">Transaction Details</div>\n" +
               "<hr>"+
               "        <p>Date: "+transaction.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+"</p>\n" +
               "        <p>Transaction ID: "+transaction.getReferenceId() +"</p>\n" +
               "        <p>Description: "+transaction.getDescription()+"</p>\n" +
               "        <p>Amount: <span class=\"transaction-amount\">"+transaction.getAmount()+"</span></p>\n" +
               "        <p>Account: "+transaction.getUser().getWallet().getAccountNumber()+"</p>\n" +
               "        <p>Payment Method: "+transaction.getCashTransferType()+" | "+transaction.getCategory()+"</p>\n" +
               "        <a class=\"button\" href=\"#\">View Transaction Details</a>\n" +
               "        <p>If you have any questions or concerns about this transaction, please contact our support team at support@moneyway.com.</p>\n" +
               "        <p>Thank you for choosing Money Way.</p>\n" +
               "        <p>Best regards,</p>\n" +
               "        <h2>The Money Way Team</h2>\n" +
               "    </div>\n" +
               "</body>\n" +
               "</html>\n";


        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY)
                .queryString("from", "MoneyWay noreply@moneyway.com")
                .queryString("to", transaction.getUser().getEmail())
                .queryString("subject", "Transaction Details")
                .queryString("html", content)
                .asJson();
        return request.getBody();
    }

}
