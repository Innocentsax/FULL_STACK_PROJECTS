package com.goCash.utils;

public class WalletBalanceUtility {

    public static double reverseDeductedFunds(double walletBalance, double amount) {
        return walletBalance + amount;
    }

    public static double subtractFunds(double walletBalance, double amount) {
        return walletBalance - amount;
    }
}
