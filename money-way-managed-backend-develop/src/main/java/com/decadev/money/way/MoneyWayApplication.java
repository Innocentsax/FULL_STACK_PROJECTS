package com.decadev.money.way;

import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.model.IncomeAndExpense;
import com.decadev.money.way.repository.IncomeAndExpenseRepository;
import com.decadev.money.way.repository.UserRepository;
import com.decadev.money.way.service.impl.SpendingAndIncomeTrendImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class MoneyWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneyWayApplication.class, args);
	}


}
