package com.decagon.dev.paybuddy.dtos.requests;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailSenderDto {

    private String to;

    private String subject;

    private String content;
}
