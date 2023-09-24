package com.decagon.kindredhairapigrp1.DTO;

import com.decagon.kindredhairapigrp1.models.Feedback;
import com.decagon.kindredhairapigrp1.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private long id;
    private String name;
    private Integer price;
    private String description;
    private String ingredients;
    private String productURL;
    private String imageURL;
    private String brand;
    private Set<Feedback> feedbacks;
    private String category;
    private String searchIngredients;
    private String searchDescription;


    public static class ProductDTOBuilder {


        private ProductDTO productDTO;

        public ProductDTOBuilder(){
            this.productDTO = new ProductDTO();
        }

        public ProductDTOBuilder setProduct(Product product, boolean setFeedback){
            this.productDTO.setBrand(product.getBrand());
            this.productDTO.setCategory(product.getCategory());
            this.productDTO.setDescription(product.getDescription());
            this.productDTO.setId(product.getId());
            this.productDTO.setImageURL(product.getImageURL());
            this.productDTO.setIngredients(product.getIngredients());
            this.productDTO.setName(product.getName());
            this.productDTO.setPrice(product.getPrice());
            this.productDTO.setProductURL(product.getProductURL());
            this.productDTO.setSearchDescription(product.getSearchDescription());
            productDTO.setSearchIngredients(product.getSearchIngredients());
            if(setFeedback){
                this.productDTO.setFeedbacks(product.getFeedbacks());
            }else{
                this.productDTO.setFeedbacks(null);
            }
            return this;
        }

        public ProductDTOBuilder setProduct(Product product){
            this.productDTO.setBrand(product.getBrand());
            this.productDTO.setCategory(product.getCategory());
            this.productDTO.setDescription(product.getDescription());
            this.productDTO.setId(product.getId());
            this.productDTO.setImageURL(product.getImageURL());
            this.productDTO.setIngredients(product.getIngredients());
            this.productDTO.setName(product.getName());
            this.productDTO.setPrice(product.getPrice());
            this.productDTO.setProductURL(product.getProductURL());
            this.productDTO.setSearchDescription(product.getSearchDescription());
            this.productDTO.setSearchIngredients(product.getSearchIngredients());
            this.productDTO.setFeedbacks(product.getFeedbacks());

            return this;
        }

        public ProductDTO build() {
            return this.productDTO;
        }
    }
}
