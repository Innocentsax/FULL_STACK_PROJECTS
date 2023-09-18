package com.example.decapay.pojos.mailDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailDto {

    private String to;
    private String subject;
    private String message;
}
