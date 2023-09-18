package com.example.decapay.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ikechi Ucheagwu
 * @created 09/12/2022 - 5:09 PM
 * @project DECAPAY
 */

@Getter
@Setter
@Entity
@Table(name = "category_tb")
public class BudgetCategory extends BaseEntity{
    private String name;
    private Boolean isDeleted;

    @ManyToOne()
    @JoinColumn(name = "user_tb_id")
    private User user;
}
