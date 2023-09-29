package com.decagon.OakLandv1be.entities;

import com.decagon.OakLandv1be.enums.TokenStatus;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "token_tbl")
@Getter
@Setter
public class Token extends BaseEntity {

    @Column(length = 500)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenStatus tokenStatus;

    @OneToOne()
    @JoinColumn(name = "person_tbl_id")
    private Person person;
}
