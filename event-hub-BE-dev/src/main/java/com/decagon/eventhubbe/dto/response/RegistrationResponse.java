package com.decagon.eventhubbe.dto.response;

import com.decagon.eventhubbe.domain.entities.ConfirmationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String firstName;
    private String lastName;
    private String message;

}
