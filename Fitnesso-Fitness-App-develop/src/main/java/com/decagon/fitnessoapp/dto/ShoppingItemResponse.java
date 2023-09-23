package com.decagon.fitnessoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingItemResponse {
    private Map<String, Integer> intangibleProduct = new HashMap<String, Integer>();
    private Map<String, Integer> tangibleProduct = new HashMap<String, Integer>();
    private Long id;

}
