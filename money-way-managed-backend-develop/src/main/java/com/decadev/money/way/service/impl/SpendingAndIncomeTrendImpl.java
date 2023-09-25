package com.decadev.money.way.service.impl;

import com.decadev.money.way.dto.response.SpendTrendResponse;
import com.decadev.money.way.dto.transaction.AmountPerWeek;
import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.model.IncomeAndExpense;
import com.decadev.money.way.model.User;
import com.decadev.money.way.repository.IncomeAndExpenseRepository;
import com.decadev.money.way.repository.TransactionRepository;
import com.decadev.money.way.service.SpendingAndIncomeTrend;
import com.decadev.money.way.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SpendingAndIncomeTrendImpl  implements SpendingAndIncomeTrend {

    private final TransactionRepository transactionRepository;

    private final IncomeAndExpenseRepository incomeAndExpenseRepository;

    private final SecurityUtils securityUtils;

    public LocalDate getCurrentLocalTime(){
        return LocalDate.now();
    }





    public void setIncomeTrend(BigDecimal amount){

        User userDetails = (User) securityUtils.getCurrentUserDetails();


        LocalDate date = getCurrentLocalTime();

        int year = date.getYear();
        int currentMonth = date.getMonthValue();
        int month = date.getMonth().getValue();
        int currentDay = date.getDayOfMonth();
        int starting = 0;
        int ending=0;
        int week = 0;


        if(currentDay>=1 && currentDay <=7){
            starting =1;
            ending=7;
            week=1;
        }else if(currentDay>=8 && currentDay <=14){
            starting =8;
            ending=14;
            week=2;
        }else if(currentDay>=15 && currentDay <=21){
            starting =15;
            ending=21;
            week=3;
        }else if(currentDay >= 22 && currentDay<=31){
            starting =22;
            ending=31;
            week=4;
        }


        List<IncomeAndExpense> incomeAndExpenses = incomeAndExpenseRepository.findIncomeAndExpenseByUserIdAndDate(year,currentMonth,starting,ending, userDetails.getId(), TransactionCategory.CREDIT.name());
        IncomeAndExpense incomeAndExpense;
        if(incomeAndExpenses.isEmpty()){

            incomeAndExpense = IncomeAndExpense.builder()
                    .userId(userDetails.getId())
                    .category(TransactionCategory.CREDIT)
                    .year(year)
                    .week(week)
                    .month(month)
                    .amount(amount.toPlainString())
                    .build();

        }else{
            IncomeAndExpense presentWeek = incomeAndExpenses.get(0);
            BigDecimal currentAmount = new BigDecimal( presentWeek.getAmount());

            currentAmount = currentAmount.add(amount);
            presentWeek.setAmount(currentAmount.toPlainString());
            incomeAndExpense = presentWeek;
        }

        incomeAndExpenseRepository.save(incomeAndExpense);


    }

    public void setSpendingTrend(BigDecimal amount){

        User userDetails = (User) securityUtils.getCurrentUserDetails();


        LocalDate date = getCurrentLocalTime();

        int year = date.getYear();
        int currentMonth = date.getMonthValue();
        int month = date.getMonth().getValue();
        int currentDay = date.getDayOfMonth();
        int starting = 0;
        int ending=0;
        int week = 0;


        if(currentDay>=1 && currentDay <=7){
            starting =1;
            ending=7;
            week=1;
        }else if(currentDay>=8 && currentDay <=14){
            starting =8;
            ending=14;
            week=2;
        }else if(currentDay>=15 && currentDay <=21){
            starting =15;
            ending=21;
            week=3;
        }else if(currentDay >= 22 && currentDay<=31){
            starting =22;
            ending=31;
            week=4;
        }


        List<IncomeAndExpense> incomeAndExpenses = incomeAndExpenseRepository.findIncomeAndExpenseByUserIdAndDate(year,currentMonth,starting,ending, userDetails.getId(), TransactionCategory.DEBIT.name());
        IncomeAndExpense incomeAndExpense;
        if(incomeAndExpenses.isEmpty()){

            incomeAndExpense = IncomeAndExpense.builder()
                    .userId(userDetails.getId())
                    .category(TransactionCategory.DEBIT)
                    .year(year)
                    .week(week)
                    .month(month)
                    .amount(amount.toPlainString())
                    .build();

        }else{
            IncomeAndExpense presentWeek = incomeAndExpenses.get(0);
            BigDecimal currentAmount = new BigDecimal( presentWeek.getAmount());

            currentAmount = currentAmount.add(amount);
            presentWeek.setAmount(currentAmount.toPlainString());
            incomeAndExpense = presentWeek;
        }

        incomeAndExpenseRepository.save(incomeAndExpense);


    }


    public SpendTrendResponse getSpendingTrend(){

        User user = (User) securityUtils.getCurrentUserDetails();
        LocalDate date = getCurrentLocalTime();

        int year = date.getYear();

        Map<Integer, Set<AmountPerWeek>> monthData = new TreeMap<>();
        List<IncomeAndExpense> incomeAndExpenses = incomeAndExpenseRepository.findIncomeAndExpenseByUserIdAndYearAndCategoryOrderByDateAsc(user.getId(), year,TransactionCategory.DEBIT);

    for(IncomeAndExpense incomeAndExpense: incomeAndExpenses){
        int month = incomeAndExpense.getMonth();
        AmountPerWeek amountPerWeek = AmountPerWeek.builder()
                .week(incomeAndExpense.getWeek())
                .amount(incomeAndExpense.getAmount())
                .build();
        if(monthData.containsKey(month)){
            monthData.get(month).add(amountPerWeek);
        }else{
           Set<AmountPerWeek> set = new TreeSet<>();
            set.add(amountPerWeek);
            monthData.put(month,set);

        }

    }



    return SpendTrendResponse.builder()
            .trendType("Spending Trend")
            .monthData(monthData)
            .build();

    }

}
