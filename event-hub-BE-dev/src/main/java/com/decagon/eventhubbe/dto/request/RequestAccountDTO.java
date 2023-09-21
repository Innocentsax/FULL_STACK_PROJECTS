package com.decagon.eventhubbe.dto.request;

import com.decagon.eventhubbe.domain.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccountDTO {

    private String bankName;
    private String accountNumber;
    private String accountName;

}
