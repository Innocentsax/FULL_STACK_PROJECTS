package com.decagon.OakLandv1be.config.companyConfig;

import com.decagon.OakLandv1be.config.companyConfig.SuperAdminRepository;
import com.decagon.OakLandv1be.entities.*;
import com.decagon.OakLandv1be.enums.BaseCurrency;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;

import com.decagon.OakLandv1be.exceptions.InvalidOperationException;

import com.decagon.OakLandv1be.repositries.AdminRepository;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AdminDetailsService implements CommandLineRunner {
    private final ProductRepository productRepository;
    private final SuperAdminRepository superAdminRepository;
    private final PasswordEncoder passwordEncoder;

    Admin admin = new Admin();

    private final AdminRepository adminRepository;

    private final PersonRepository personRepository;

    @Value("${admin.super.email}")
    private String adminEmail;

    @Value("${admin.currency}")
    private String currency;
    String password = UUID.randomUUID().toString();


    public void initAdmin() {

        if (superAdminRepository.findAll().size() > 0) {
            throw new InvalidOperationException("Operation NOT ALLOWED");
        }


        Person person = Person.builder()
                .date_of_birth(new Date().toString())
                .email(adminEmail)
                .firstName("super")
                .lastName("Admin")
                .role(Role.ADMIN)
                .gender(Gender.OTHER)
                .isActive(true)
                .password(passwordEncoder.encode(password))
                .verificationStatus(true)
                .build();

        Wallet wallet = Wallet.builder()
                .accountBalance(BigDecimal.ZERO)
                .baseCurrency(BaseCurrency.valueOf(currency))
                .build();
        SuperAdmin superAdmin = SuperAdmin.builder()
                .person(person)
                .wallet(wallet)
                .build();

        SuperAdmin superAdminDB = superAdminRepository.save(superAdmin);
        log.info("#############################################################################");
        log.info("Super Admin Details");

        log.info("Username/Email: \t" + password);
        log.info("Password/Passphrase: \t" + adminEmail);

        log.info("*******************   Wallet Information   *****************");
        log.info(superAdminDB.getWallet().toString());

        log.info("#############################################################################");


//        if (!personRepository.existsByEmail("bennyson1@gmail.com")) {
//            Customer customer = new Customer();
//
//            Person person1 = Person.builder()
//                    .firstName("Benson")
//                    .lastName("Malik")
//                    .email("bennyson1@gmail.com")
//                    .gender(Gender.MALE)
//                    .date_of_birth("13-08-1990")
//                    .phone("9859595959")
//                    .isActive(true)
//                    .verificationStatus(true)
////                        .password(passwordEncoder.encode("password123"))
////                        .role(Role.CUSTOMER)
//                    .customer(customer)
//                    .address("No Address")
//                    .role(Role.ADMIN)
//                    .password(passwordEncoder.encode("password123453"))
//                    .isActive(true)
//
//                    .build();
//            personRepository.save(person);
//
//            //customer.setPerson(person);
//            adminRepository.save(admin);
//
//
//        }

//    @Override
//    public void run(String... args) throws Exception {
//        initAdmin();
//
//    }
    }

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
    }
}

