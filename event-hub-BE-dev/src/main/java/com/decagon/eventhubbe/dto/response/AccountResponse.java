package com.decagon.eventhubbe.dto.response;

import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {
    private String messsage;
    private LocalDateTime localDateTime;
    private Object object;
}
