package com.decagon.fitnessoapp.model.product;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "stock_keeping_unit",nullable = false, unique = true)
    private String stockKeepingUnit = UUID.randomUUID().toString();

    @NotNull
    @Column(nullable = false)
    private Long stock;

    @NotNull
    @Column(nullable = false)
    private String category;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 10485760)
    private String description;

    private String image;
}
