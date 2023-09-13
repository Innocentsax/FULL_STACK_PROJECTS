package com.decagon.adire.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private  String address;

}
