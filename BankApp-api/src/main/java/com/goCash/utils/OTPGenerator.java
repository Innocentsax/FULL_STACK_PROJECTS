package com.goCash.utils;

import java.util.Random;

public class OTPGenerator {

    public static String OTP() {
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(9);
            sb.append(randomIndex);
        }
        return sb.toString();
    }

//    public static void main(String[] args) {
//        String randomString = OTP();
//        System.out.println("OTP: " + randomString);
//    }
}

