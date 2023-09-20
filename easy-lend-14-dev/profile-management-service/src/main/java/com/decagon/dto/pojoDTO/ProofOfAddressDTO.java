package com.decagon.dto.pojoDTO;

import com.decagon.domain.screen.ProofOfAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProofOfAddressDTO {
    private String documentUrl;

    public ProofOfAddressDTO(ProofOfAddress proof) {
        this.documentUrl = proof.getDocument_Url();
    }
}
