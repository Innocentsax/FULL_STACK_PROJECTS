package com.decagon.safariwebstore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ordered_items")
@EqualsAndHashCode(callSuper = true)
public class OrderedItem extends BaseModel {

    private Long productId;

    private String quantity;

    @Column(columnDefinition = "decimal")
    private Double price;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String size;

    private String color;

    private String productImage;

}
