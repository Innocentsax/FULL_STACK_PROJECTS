package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "modeOfPayment_tbl")
public class ModeOfPayment extends BaseEntity{

    private String name;

    private String provider;

    @JsonIgnore
    @OneToOne(mappedBy = "modeOfPayment", cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}
