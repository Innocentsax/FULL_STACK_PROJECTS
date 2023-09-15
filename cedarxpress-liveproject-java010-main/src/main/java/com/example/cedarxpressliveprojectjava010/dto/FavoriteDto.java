package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDto {

    private Product product;

    private User user;
}
