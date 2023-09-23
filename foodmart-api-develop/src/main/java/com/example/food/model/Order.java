package com.example.food.model;

import com.example.food.Enum.DeliveryMethod;
import com.example.food.Enum.OrderStatus;
import com.example.food.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageUrl;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date modifiedAt;
    private Integer quantity;
    @Enumerated
    private OrderStatus orderStatus;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Users user;
    @OneToMany
    @JoinColumn(name = "order_product_id")
    private List<Product> productList;
    @OneToMany
    private List<OrderedItem> orderedItem;
    @JsonIgnore
    @OneToOne
    private Address address;
    private PaymentMethod paymentMethod;
    private BigDecimal deliveryFee;
    private BigDecimal discount;
    private DeliveryMethod deliveryMethod;
    private BigDecimal subTotal;
    private BigDecimal totalOrderPrice;
}
