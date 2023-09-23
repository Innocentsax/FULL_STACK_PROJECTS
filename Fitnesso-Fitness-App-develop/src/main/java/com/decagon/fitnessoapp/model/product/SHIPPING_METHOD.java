package com.decagon.fitnessoapp.model.product;


public enum SHIPPING_METHOD {

    FLAT_RATE(200),
    EXPEDITED_SHIPPING(300),
    OVERNIGHT_SHIPPING(400);

    public final Integer delivery;

    private SHIPPING_METHOD(Integer delivery){
        this.delivery = delivery;
    }
}
