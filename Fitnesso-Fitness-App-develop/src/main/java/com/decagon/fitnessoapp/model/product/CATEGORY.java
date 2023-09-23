package com.decagon.fitnessoapp.model.product;

public enum CATEGORY {

    CLOTHES("Clothes"),
    GYM_EQUIPMENTS("Gym_Equipments"),
    SUPPLEMENTS("Supplements"),
    TRAINERS("Trainers");

    private final String category;

    CATEGORY(String category) {
        this.category = category;
    }
}
