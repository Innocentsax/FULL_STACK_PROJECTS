package com.decagon.dto.request;


import com.decagon.dto.pojoDTO.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequestDTO {
    private Long user_id;
    private String profileCreationStatus;
    private ContactInformationDTO contactInformation;
    private EmploymentStatusDTO employmentStatus;
    private GovernmentIDDTO governmentID;
    private IncomeStatusDTO incomeStatus;
    private ProofOfAddressDTO proofOfAddress;
    private BankAccountDTO bankAccount;
}