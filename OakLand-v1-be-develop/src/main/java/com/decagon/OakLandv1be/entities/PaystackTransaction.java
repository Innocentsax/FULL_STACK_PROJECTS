package com.decagon.OakLandv1be.entities;


import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaystackTransaction extends BaseEntity{

    private String reference;
    private Boolean success;
    private String email;
}
