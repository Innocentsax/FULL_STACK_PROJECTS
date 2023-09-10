package com.goCash.enums;

public enum TransactionStatus {

    SUCCESSFUL(0), PENDING(1), FAILED(2);

    private int id;

    TransactionStatus(int id) {
        this.id = id;
    }
}
