package com.decagon.fintechpaymentapisqd11a.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailServiceDto {
    private String name;
    private String emailAddress;
    private String message;
    private String subject;


    @Override
    public String toString() {

        return "UserDto [name=" + name + ", address = " + emailAddress + ", message = " + message + ", subject = " + subject + "]";
    }
}