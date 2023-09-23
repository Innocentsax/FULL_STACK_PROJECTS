package com.decagon.fitnessoapp.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "Person_id", referencedColumnName = "id")
    private Person person;


    @Column(nullable = false)
    private Long productId;

}
