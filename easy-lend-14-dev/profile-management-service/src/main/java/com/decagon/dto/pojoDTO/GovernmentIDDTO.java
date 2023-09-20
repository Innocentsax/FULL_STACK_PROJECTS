package com.decagon.dto.pojoDTO;

import com.decagon.domain.screen.GovernmentID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GovernmentIDDTO {
    private String documentType;
    private String documentNumber;
    private String url;

    public GovernmentIDDTO(GovernmentID governmentID) {
        this.documentType = governmentID.getDocumentType();
        this.documentNumber = governmentID.getDocumentNumber();
        this.url = governmentID.getDocumentUrl();
    }
}