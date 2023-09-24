package com.decagon.kindredhairapigrp1.DTO;

import com.decagon.kindredhairapigrp1.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyProductDTO {

    private long id;
    private String title;
    private String body_html = "";
    private String vendor;
    private String product_type;
    private List<Map<String, String>> variants;
    private List<Map<String, String>> images;

    public static class ShopifyProductDTOBuilder {
        private ShopifyProductDTO shopifyProductDTO;

        public ShopifyProductDTOBuilder setProduct(Product product){
            shopifyProductDTO = new ShopifyProductDTO();
            Map<String, String> image = new HashMap<>();
            image.put("src", product.getImageURL());

            Map<String, String> variant = new HashMap<>();
            variant.put("price", String.valueOf(product.getPrice()));

            this.shopifyProductDTO.setId(product.getShopifyID());
            this.shopifyProductDTO.setTitle(product.getName());
            this.shopifyProductDTO.setVendor(product.getBrand());
            this.shopifyProductDTO.setProduct_type(product.getCategory());
            this.shopifyProductDTO.setVariants(List.of(variant));
            this.shopifyProductDTO.setImages(List.of(image));

            return this;
        }

        public ShopifyProductDTO build() {
            return this.shopifyProductDTO;
        }
    }
}