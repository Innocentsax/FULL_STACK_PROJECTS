package com.example.decapay.models;

import com.example.decapay.enums.Status;
import com.example.decapay.enums.VerificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "token_tb")
@Entity
public class Token extends BaseEntity {

    private String token;

    @Enumerated(value = EnumType.STRING)
    private VerificationType verificationType;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "user_tb_id")
    private User user;
}
