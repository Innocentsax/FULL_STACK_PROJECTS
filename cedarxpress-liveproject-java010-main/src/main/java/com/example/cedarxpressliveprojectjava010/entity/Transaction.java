package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction extends Base{
    private String email;
    private String amount;
    private Payment payment;
    private String reference;
}
