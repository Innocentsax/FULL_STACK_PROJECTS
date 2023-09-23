package com.example.hive.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    TASKER("ROLE_TASKER"), DOER("ROLE_DOER");

    private final String role;
}
