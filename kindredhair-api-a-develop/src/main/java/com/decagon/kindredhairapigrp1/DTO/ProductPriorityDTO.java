package com.decagon.kindredhairapigrp1.DTO;

import lombok.*;

import java.util.Objects;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriorityDTO {
        private ProductDTO productDTO;
        private int ingredientPriority = 0;
        private int descriptionPriority = 0;
        private int numOfIngredients= 0;
        private int numOfDescription = 0;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductPriorityDTO that = (ProductPriorityDTO) o;
                return productDTO.getId() == that.productDTO.getId();
        }
        @Override
        public int hashCode() {
                return Objects.hash(productDTO);
        }
}
