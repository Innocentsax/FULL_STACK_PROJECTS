package com.decagon.domain.screen;

import com.decagon.dto.pojoDTO.ProofOfAddressDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProofOfAddress {
    private String document_Url;

    public ProofOfAddress(ProofOfAddressDTO proofOfAddressDTO) {
        this.document_Url = proofOfAddressDTO.getDocumentUrl();
    }
}