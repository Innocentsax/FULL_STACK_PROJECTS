package com.decadev.money.way.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "networks")
public class Network {
    @Id
    private Integer id;
    @NonNull
    private String biller_code;
    private String name;
    private String default_commission;
    private String date_added;
    private String country;
    private boolean is_airtime;
    @NonNull
    private String biller_name;
    private String item_code;
    private String short_name;
    private String fee;
    private boolean commission_on_fee;
    private String label_name;
    private String amount;
}
