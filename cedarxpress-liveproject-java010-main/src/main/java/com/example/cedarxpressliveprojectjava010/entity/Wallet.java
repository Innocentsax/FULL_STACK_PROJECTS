package com.example.cedarxpressliveprojectjava010.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Wallet extends Base{

    private BigDecimal balance = new BigDecimal(0);

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private User user;

}
