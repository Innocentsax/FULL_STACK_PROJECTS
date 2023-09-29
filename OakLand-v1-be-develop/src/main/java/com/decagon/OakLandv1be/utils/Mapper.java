package com.decagon.OakLandv1be.utils;

import com.decagon.OakLandv1be.dto.CustomerProfileDto;
import com.decagon.OakLandv1be.dto.ProductCustResponseDto;
import com.decagon.OakLandv1be.dto.ProductResponseDto;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Product;

public class Mapper {

    public  static ProductCustResponseDto productToProductResponseDto(Product product){
        return ProductCustResponseDto.builder()
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .color(product.getColor())
                .description(product.getDescription())
                .subCategory(product.getSubCategory())
                .build();
    }

    public static CustomerProfileDto customerToCustomerProfileDto (Customer customer){
        return CustomerProfileDto.builder()
                .firstName(customer.getPerson().getFirstName())
                .lastName(customer.getPerson().getLastName())
                .email(customer.getPerson().getEmail())
                .gender(customer.getPerson().getGender())
                .date_of_birth(customer.getPerson().getDate_of_birth())
                .phone(customer.getPerson().getPhone())
                .verificationStatus(customer.getPerson().getVerificationStatus())
                .address(customer.getPerson().getAddress())
                .build();
    }
}
