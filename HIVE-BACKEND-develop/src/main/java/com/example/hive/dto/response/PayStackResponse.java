package com.example.hive.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
@Getter
@Setter
public class PayStackResponse {

    private boolean status;
    private String message;
    private Data data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Component
    @Getter
    @Setter
    public static class Data {
        /**
         * this is the redirect url that the user would use to make the payment
         */
        private String authorization_url;
        /**
         * this code identifies the payment url
         */
        private String access_code;
        /**
         * the unique reference used to identify this transaction
         */
        private String reference;
    }

}
