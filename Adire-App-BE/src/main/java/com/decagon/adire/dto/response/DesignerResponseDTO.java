package com.decagon.adire.dto.response;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DesignerResponseDTO {

    private String firstName;

    private  String lastName;

    private String email;

    private  String phoneNumber;
    private String url;
    
}
