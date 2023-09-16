package com.decagon.chompapp.dtos.paystack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    private String authorization_url;
    private String access_code;
    private String reference;
}
