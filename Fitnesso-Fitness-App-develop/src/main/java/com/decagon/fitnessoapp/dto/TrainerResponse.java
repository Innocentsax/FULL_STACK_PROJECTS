package com.decagon.fitnessoapp.dto;

import lombok.Data;

@Data
public class TrainerResponse {
    private PersonInfoResponse trainerInfo;
    private String description;
}
