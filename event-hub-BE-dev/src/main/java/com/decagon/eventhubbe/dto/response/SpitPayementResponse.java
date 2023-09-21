package com.decagon.eventhubbe.dto.response;

import lombok.Data;

@Data
public class SpitPayementResponse {
            private String authorization_url;
            private String access_code;
            private String reference;
}
