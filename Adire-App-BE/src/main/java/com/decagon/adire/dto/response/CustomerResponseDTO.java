package com.decagon.adire.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerResponseDTO {
    private String id;
    private String firstName;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String address;
    private Timestamp createdDate;
}
