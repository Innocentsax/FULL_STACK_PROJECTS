package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.ContactInformationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactInformation {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public ContactInformation(ContactInformationDTO contactInformationDTO) {
        this.firstName = contactInformationDTO.getFirstName();
        this.lastName = contactInformationDTO.getLastName();
        this.email = contactInformationDTO.getEmail();
        this.phoneNumber = contactInformationDTO.getPhoneNumber();
    }
}