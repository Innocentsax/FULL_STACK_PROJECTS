package com.example.hive.constant;

import lombok.Getter;

@Getter
public enum ResponseStatus {

    SUCCESSFUL("00", "success"),
    ERROR("06", "error"),
    REQUEST_IN_PROGRESS("09", "request in progress"),
    INSUFFICIENT_FUNDS("51", "insufficient fund"),
    SYSTEM_ERROR("96", "system error"),
    NOT_FOUND("56", "not found"),
    FORMAT_ERROR("30", "format error");

    private final String code;
    private final String message;

    ResponseStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
