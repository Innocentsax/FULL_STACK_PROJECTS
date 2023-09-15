package com.example.cedarxpressliveprojectjava010.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EmailSenderDto {
    private String to;
    private String subject;
    private String content;
}
