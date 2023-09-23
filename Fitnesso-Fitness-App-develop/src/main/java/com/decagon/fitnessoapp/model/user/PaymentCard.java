package com.decagon.fitnessoapp.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PaymentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
//    @Column(name = "card_number", unique = true, nullable = false)
    @Column(name = "card_number", nullable = false)
    private Long cardNumber;

    @Column(name = "expiring_date", nullable = false)
    private String expiringDate;

    @Column(name = "cvv_number", nullable = false)
    private Integer cvvNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
