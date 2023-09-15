package com.example.cedarxpressliveprojectjava010.dto.frontEndDto;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private Long id;
    private List<CartItemDto> cartItemDto = new ArrayList<>();
}