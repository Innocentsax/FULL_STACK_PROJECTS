// package com.decagon.safariwebstore.utils.mailService;


// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.mashape.unirest.http.HttpResponse;
// import com.mashape.unirest.http.JsonNode;
// import com.mashape.unirest.http.Unirest;
// import com.mashape.unirest.http.exceptions.UnirestException;
// import lombok.Data;
// import org.junit.jupiter.api.Test;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.assertj.core.api.Assertions.assertThatThrownBy;

// class MailGIntegrationApplicationTests {


//     @Test
//     void contextLoads() throws UnirestException, JsonProcessingException {
//         HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "divineworld.club" + "/messages")
//                 .basicAuth("api", API_KEY.API)
//                 .field("from", "safari@safari-webstore.com")
//                 .field("to", "feuzsender@gmail.com")
//                 .field("subject", "Test mailgun, from group B (Safari clothing store")
//                 .field("text", "THIS IS DEFINATELY THE LAST ONE SIR, HAHAHAHAHAHAHHAH Sir, this is just for testing purpose, if you receive this\" +\n" +
//                         "                        \" it means we have successfully integrated the mail service, " +
//                         "FINALLY IT HAS WORKED O O !!!!!!!!!!!!")
//                 .asJson();

//         String body = request.getBody().toString();

//         ObjectMapper objectMapper = new ObjectMapper();
//         MessageAfterSending messageAfterSending = objectMapper.readValue(body, MessageAfterSending.class);

//         assertThat(body).contains("message");

//     }

//     @Test
//     void exceptionTest() throws UnirestException, JsonProcessingException {

//         assertThatThrownBy(() -> {
//             HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "wrongdomain.club" + "/messages")
//                     .basicAuth("api", API_KEY.API)
//                     .field("from", "safari@safari-webstore.com")
//                     .field("to", "feuzsender@gmail.com")
//                     .field("subject", "Test mailgun, from group B (Safari clothing store")
//                     .field("text", "THIS IS DEFINATELY THE LAST ONE SIR, HAHAHAHAHAHAHHAH Sir, this is just for testing purpose, if you receive this\" +\n" +
//                             "                        \" it means we have successfully integrated the mail service, " +
//                             "FINALLY IT HAS WORKED O O !!!!!!!!!!!!")
//                     .asJson();
//         }).hasMessageContaining("java.lang.RuntimeException: java.lang.RuntimeException: org.json.JSONException: A JSONArray text must start with '[' at"+" 1 ["+"character 2 line 1"+"]");


//     }

// }

// @Data
// class MessageAfterSending {
//     public String id;
//     public String message;
// }
