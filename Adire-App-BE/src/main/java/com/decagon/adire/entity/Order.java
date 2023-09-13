package com.decagon.adire.entity;

import com.decagon.adire.enums.MaterialType;
import com.decagon.adire.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private MaterialType materialType;
    private String duration;
    private  Double orderPrice;
    private LocalDateTime dueDate;
    private  String designImageUrl;

    @ManyToOne
    @JoinColumn(name ="customer_id", referencedColumnName = "id")
    private Customer customer;
    @OneToOne(mappedBy = "order")
    private Receipt receipt;
    @ManyToOne
    @JoinColumn(name = "designerId", referencedColumnName = "id")
    private Designer designer;
    @OneToMany
    private List<Measurement> measurementList;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
