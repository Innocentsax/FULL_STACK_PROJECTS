package com.decagon.OakLandv1be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "state_tbl")
public class State extends BaseEntity{

   private String name;

   @JsonIgnore
   @OneToMany(mappedBy = "state" ,cascade = CascadeType.ALL)
   private Set<PickupCenter> pickupCenters;

}
