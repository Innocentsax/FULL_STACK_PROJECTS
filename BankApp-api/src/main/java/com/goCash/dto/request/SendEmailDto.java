package com.goCash.dto.request;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailDto {

    private String firstname;
    private String toEmail;
    private String message;
    private String subject;

}
