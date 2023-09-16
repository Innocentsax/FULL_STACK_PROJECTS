package com.decagon.chompapp.models;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
@ToString
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;

    private @NotNull String size;
    private String productImage;

    private Long quantity;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date createdDate;

    private double productPrice;

    @ManyToOne
    private Category category;


}
