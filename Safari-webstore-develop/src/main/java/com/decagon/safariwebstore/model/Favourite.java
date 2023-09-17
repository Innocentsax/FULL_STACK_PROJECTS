package com.decagon.safariwebstore.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "favorite_products")
@AllArgsConstructor
@NoArgsConstructor
public class Favourite extends BaseModel{

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;


}
