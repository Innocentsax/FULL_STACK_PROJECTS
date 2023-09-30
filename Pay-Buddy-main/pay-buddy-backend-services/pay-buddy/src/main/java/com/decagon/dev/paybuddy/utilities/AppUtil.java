package com.decagon.dev.paybuddy.utilities;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AppUtil {

    public boolean validImage(String fileName)
    {
        String regex = "(.*/)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
        Pattern p = Pattern.compile(regex);
        if (fileName == null) {
            return false;
        }
        Matcher m = p.matcher(fileName);
        return m.matches();
    }

    //Email validation
    public boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String generateSerialNumber(String prefix) {
        Random rand = new Random();
        long x = (long) (rand.nextDouble() * 100000000000000L);
        return prefix + String.format("%014d", x);
    }

    public String getFormattedNumber(final String number){
        String trimmedNumber = number.trim();
        String formattedNumber = null;
        if(trimmedNumber.startsWith("0"))
            formattedNumber = "+234" + trimmedNumber.substring(1);
        else if(trimmedNumber.startsWith("234"))
            formattedNumber = "+" + number;
        else if (!number.startsWith("+")
                && Integer.parseInt(String.valueOf(number.charAt(0))) > 0) {
            formattedNumber = "+234" + number;
        }
        return  formattedNumber;
    }
    public String generateAccountNumber(Long userId, String email) {
        int randomNumber = Math.abs(ThreadLocalRandom.current().nextInt(1000000000, 2000000000));
        String combinedString = userId + Math.abs(email.hashCode()) + String.valueOf(randomNumber);
        return combinedString.substring(0, 10);
    }
    public String concealAccountNumber(String accountNumber) {
        String obscured = "XXXXXXXX" + accountNumber.substring(6);
        return obscured.substring(obscured.length() - 10);
    }

}
