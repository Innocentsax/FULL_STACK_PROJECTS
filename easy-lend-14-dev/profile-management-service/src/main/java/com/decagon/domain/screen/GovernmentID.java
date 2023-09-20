package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.GovernmentIDDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GovernmentID {
    private String documentType;
    private String documentNumber;
    private String documentUrl;

    public GovernmentID(GovernmentIDDTO governmentIDDTO) {
        this.documentType = governmentIDDTO.getDocumentType();
        this.documentNumber = governmentIDDTO.getDocumentNumber();
        this.documentUrl = governmentIDDTO.getUrl();
    }
}