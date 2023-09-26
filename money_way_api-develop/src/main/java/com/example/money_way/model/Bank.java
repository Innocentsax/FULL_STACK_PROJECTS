package com.example.money_way.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bankL_list_tbl")
public class Bank extends Base{
    private String bankName;
    private String bankCode;
}
