package com.decagon.kindredhairapigrp1.models;

import com.decagon.kindredhairapigrp1.models.Basemodel.BaseModel;
import lombok.*;


import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="user_details")
public class UserDetails extends BaseModel {

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ShippingAddress> shippingAddress;

    private  String firstname;

    private  String lastname;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DislikedBrand> dislikedBrand;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL)
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private  Set<UserFavorite> UserFavorites;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Payment> payments;

    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL)
    private Set<UserHairProfile> userHairProfile;
    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL)
    private Set<ProductRecommendation> productRecommendation;
    @OneToMany(mappedBy = "userDetails", cascade =  CascadeType.ALL)
    private Set<UserHairEducation> userHairEducation;
    @OneToOne
    private User user;

}