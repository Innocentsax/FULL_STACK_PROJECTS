package com.decagon.fitnessoapp.dto;

import com.decagon.fitnessoapp.model.product.SHIPPING_METHOD;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CheckOutRequest {
    @NotEmpty
    private String email;
//    @NotNull
    private Long shoppingCartId;
    private String shoppingCartUniqueId;
    private AddressRequest shippingAddress;
    private AddressRequest billingAddress;
    private PaymentRequest paymentRequest;
    private DiscountRequest discountRequest;
    @NotNull
    private SHIPPING_METHOD shippingMethod;
    @NotNull
    private OrderSummaryRequest orderSummary;
}
