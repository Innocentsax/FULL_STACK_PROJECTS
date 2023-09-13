package com.decagon.adire.security;

import java.util.Random;

public class RandomGenerator {
    public static String generateOtp(){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
