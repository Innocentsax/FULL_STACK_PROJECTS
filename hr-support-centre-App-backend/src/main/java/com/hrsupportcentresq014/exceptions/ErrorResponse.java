package com.hrsupportcentresq014.exceptions;

import lombok.*;


import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class ErrorResponse {
    private int errorCode;
    private String response;
    private LocalDate timestamp;
}
