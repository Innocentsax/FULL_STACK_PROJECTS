package com.example.money_way.model;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_tbl")
public class User extends Person{
    @Column(nullable = false)
    private String pin;
    private String bvn;
    private String confirmationToken;
    private boolean isActive;
}