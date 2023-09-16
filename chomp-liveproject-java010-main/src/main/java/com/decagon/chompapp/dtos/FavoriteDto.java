package com.decagon.chompapp.dtos;

import com.decagon.chompapp.models.Product;
import com.decagon.chompapp.models.User;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDto {

    private Product product;

    private User user;
}
