package com.decagon.fitnessoapp.service.serviceImplementation;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public boolean validatePhoneNumber(String phoneNumber){
        Pattern p = Pattern.compile("^[0]\\d{10}$|^[\\+]?[234]\\d{12}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }
}
