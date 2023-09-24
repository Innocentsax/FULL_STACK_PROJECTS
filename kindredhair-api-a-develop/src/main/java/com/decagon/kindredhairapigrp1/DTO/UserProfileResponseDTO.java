package com.decagon.kindredhairapigrp1.DTO;

import com.decagon.kindredhairapigrp1.models.CartItem;
import com.decagon.kindredhairapigrp1.models.UserFavorite;
import com.decagon.kindredhairapigrp1.models.UserHairProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {
    private  String firstname;
    private  String lastname;
    private Set<CartItem> cartItems;
    private Set<UserHairProfile> userHairProfile;
    private Set<UserFavorite> UserFavorites;


}
