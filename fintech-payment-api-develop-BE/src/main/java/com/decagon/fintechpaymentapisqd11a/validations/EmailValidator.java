package com.decagon.fintechpaymentapisqd11a.validations;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;


@Service
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {

        return true;
    }
}
