package com.decagon.dto.pojoDTO;

import com.decagon.domain.screen.ContactInformation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactInformationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public ContactInformationDTO(ContactInformation contactInformation) {
        this.firstName = contactInformation.getFirstName();
        this.lastName = contactInformation.getLastName();
        this.email = contactInformation.getEmail();
        this.phoneNumber = contactInformation.getPhoneNumber();
    }
}
