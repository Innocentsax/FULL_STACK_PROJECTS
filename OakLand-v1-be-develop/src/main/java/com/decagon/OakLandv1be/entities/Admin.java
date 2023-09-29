package com.decagon.OakLandv1be.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "admin_tbl")
public class Admin extends BaseEntity{


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;


}
