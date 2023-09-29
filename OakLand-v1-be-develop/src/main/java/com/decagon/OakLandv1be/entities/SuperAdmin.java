package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "super_admin_tbl")
public class SuperAdmin extends BaseEntity{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
// some changes
//chage-2 