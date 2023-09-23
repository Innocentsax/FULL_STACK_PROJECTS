package com.decagon.fitnessoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoResponse {
    private String email;
    private String userName;
    private String firstName;
    private String lastName;
    private String gender;
    private String image;
    private String phoneNumber;
    private Date dateOfBirth;
    private String dobText;
    private AddressRequest address;

    public String setDate(Date date) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        final String dobText = simpleDateFormat.format(date);
        return dobText;
    }
}
