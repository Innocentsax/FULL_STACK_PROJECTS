package com.decagon.fitnessoapp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "street_detail", nullable = false)
    private String streetDetail;

    @NotNull
    @Column(nullable = false)
    private String state;

    @NotNull
    @Column(nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Person person;

    @NotNull
    @Column(nullable = false)
    private String country;

}
