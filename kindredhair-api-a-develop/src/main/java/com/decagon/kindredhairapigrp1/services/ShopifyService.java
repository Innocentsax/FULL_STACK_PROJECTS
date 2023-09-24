package com.decagon.kindredhairapigrp1.services;

import com.decagon.kindredhairapigrp1.models.Product;

public interface ShopifyService {

    boolean uploadProductToShopify(Product product);
    boolean updateProductOnShopify(Product product);
    void handleProductUploadsAndUpdateToShopify();
}