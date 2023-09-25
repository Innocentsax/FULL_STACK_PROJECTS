package com.decadev.money.way.utils;

import com.decadev.money.way.enums.Role;
import com.decadev.money.way.enums.TransactionType;
import com.decadev.money.way.model.Beneficiary;
import com.decadev.money.way.model.User;
import com.decadev.money.way.model.Wallet;
import com.decadev.money.way.repository.BeneficiaryRepository;
import com.decadev.money.way.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
public class DataSeeder implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BeneficiaryRepository beneficiaryRepository;


    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .email("sam@gmail.com")
                .password(passwordEncoder.encode("12345abc@"))
                .enabled(true)
                .firstName("Samuel")
                .lastName("Grill")
                .pin(passwordEncoder.encode("7777"))
                .role(Role.USER)
                .phoneNumber("09043995479")
                .build();
        User user2 = User.builder()
                .email("javamailverify@gmail.com")
                .password(passwordEncoder.encode("12345abc@"))
                .enabled(true)
                .firstName("Dickson")
                .pin(passwordEncoder.encode("7777"))
                .lastName("Trump")
                .role(Role.USER)
                .phoneNumber("09043995479")
                .build();
        User savedUser = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        Wallet wallet = Wallet.builder()
                .user(savedUser)
                .bankName("Wema")
                .accountNumber("1234567890")
                .active(true)
                .accountBalance("50000")
                .build();
        Wallet wallet2 = Wallet.builder()
                .user(savedUser2)
                .bankName("Wema")
                .accountNumber("9234567893")
                .active(true)
                .accountBalance("50000")
                .build();

        savedUser.setWallet(wallet);
        savedUser2.setWallet(wallet2);

        Beneficiary ben1 = Beneficiary.builder()
                .bank("Wema")
                .accountNumber("09043215")
                .name("Dayo Kuku")
                .transactionType(TransactionType.CASH_TRANSFER)
                .cashTransferType("Local transfer")
                .user(savedUser)
                .build();

        Beneficiary ben2 = Beneficiary.builder()
                .bank("Wema")
                .accountNumber("14043216")
                .name("Lionel Chukwuemeka")
                .cashTransferType("Local transfer")
                .transactionType(TransactionType.CASH_TRANSFER)
                .user(savedUser)
                .build();

        Beneficiary ben3 = Beneficiary.builder()
                .bank("Wema")
                .accountNumber("53043216")
                .name("Panshak James")
                .transactionType(TransactionType.CASH_TRANSFER)
                .cashTransferType("OTHER BANKS")
                .user(savedUser2)
                .build();

        Beneficiary ben4 = Beneficiary.builder()
                .phoneNumber("08064838214")
                .transactionType(TransactionType.AIRTIME)
                .user(savedUser)
                .build();

        Beneficiary ben5 = Beneficiary.builder()
                .phoneNumber("09067838213")
                .transactionType(TransactionType.AIRTIME)
                .user(savedUser2)
                .build();

        beneficiaryRepository.saveAll(Arrays.asList(ben1, ben2, ben3, ben4, ben5));
        userRepository.saveAll(Arrays.asList(savedUser2, savedUser));
    }
}
