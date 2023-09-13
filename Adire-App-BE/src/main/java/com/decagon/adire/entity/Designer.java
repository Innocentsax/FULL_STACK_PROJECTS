package com.decagon.adire.entity;

import com.decagon.adire.enums.Provider;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "designers")
public class  Designer extends BaseEntity {
    private String firstName;
    private  String lastName;
    private String url;

    @Column(unique = true)
    private String email;
    private  String phoneNumber;
    private  String password;
    private String role;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String otp;
    private Long otpCreatedAt;
    @OneToMany(mappedBy = "designer", fetch = FetchType.EAGER)
    private Set<Customer> customers = new HashSet<>();
    @OneToMany(mappedBy = "designer", fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    private Boolean enabled;
    public Boolean isEnabled() {
        return enabled = true;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
