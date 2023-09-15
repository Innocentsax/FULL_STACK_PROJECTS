package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.AddressType;
import lombok.*;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Address extends Base{


    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String state;
    private String city;
    private String address;
    private Integer zipCode;
    private String phoneNumber;
    private Boolean isDefault;

    @ManyToOne
    private User user;

}
