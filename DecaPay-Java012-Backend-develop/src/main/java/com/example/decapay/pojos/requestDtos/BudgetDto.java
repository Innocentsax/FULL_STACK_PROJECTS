package com.example.decapay.pojos.requestDtos;

import com.example.decapay.enums.BudgetPeriod;
import com.example.decapay.exceptions.ValidationException;
import com.example.decapay.models.Budget;
import com.example.decapay.utils.DateParser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@ToString
public class BudgetDto {

    private Long id;
    @NotEmpty(message = "title field required")
    private String title;
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
    @NotNull(message = "Budget Period is required. Accepted input includes: ANNUAL, MONTHLY, WEEKLY, DAILY, CUSTOM")
    private String budgetPeriod;
    private LocalDateTime updatedAt;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Date should be in yyyy-MM-dd format.")
    private String startDate;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format. Date should be in yyyy-MM-dd format.")
    private String endDate;
    @NotEmpty(message = "description required")
    private String description;
    @Min(2022)
    @Max(2100)
    private int year;
    @Min(1)
    @Max(12)
    private int month;


    public static void mapBudgetDtoToBudget(BudgetDto budgetDto, Budget budget) {
        budget.setUpdatedAt(budgetDto.getUpdatedAt());
        String period = budgetDto.getBudgetPeriod();
        budget.setBudgetPeriod(BudgetPeriod.valueOf(period));
        LocalDate year = LocalDate.of(budgetDto.getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        if (budgetDto.getBudgetPeriod().equals(String.valueOf(BudgetPeriod.ANNUAL))) {
            if (year.isBefore(LocalDate.now()))
                throw new ValidationException("Invalid Date. Please enter a new year.");
            else {
                budget.setStartDate(year);
                budget.setEndDate(year.plusYears(1L));
            }
        } else if (budgetDto.getBudgetPeriod().equals(String.valueOf(BudgetPeriod.MONTHLY))) {
            LocalDate month = LocalDate.of(budgetDto.getYear(), budgetDto.getMonth(), LocalDate.now().getDayOfMonth());
            if (month.isBefore(LocalDate.now()))
                throw new ValidationException("Invalid Date. Please enter a new month.");
            else {
                budget.setStartDate(month);
                budget.setEndDate(month.plusMonths(1L));
            }
        } else if (budgetDto.getBudgetPeriod().equals(String.valueOf(BudgetPeriod.WEEKLY))) {
            if (DateParser.parseDate(budgetDto.getStartDate()).isBefore
                    (LocalDate.now()))
                throw new ValidationException("Invalid Date. Please enter a valid date.");
            else {
                budget.setStartDate(DateParser.parseDate(budgetDto.getStartDate()));
                budget.setEndDate(DateParser.parseDate(budgetDto.getStartDate()).plusWeeks(1L));
            }
        } else if (budgetDto.getBudgetPeriod().equals(String.valueOf(BudgetPeriod.DAILY))) {
            if (DateParser.parseDate(budgetDto.getStartDate()).isBefore
                    (LocalDate.now()))
                throw new ValidationException("Invalid Date. Please enter a valid date.");
            else {
                budget.setStartDate(DateParser.parseDate(budgetDto.getStartDate()));
                budget.setEndDate(DateParser.parseDate(budgetDto.getStartDate()).plusDays(1L));
            }
        } else if (budgetDto.getBudgetPeriod().equals(String.valueOf(BudgetPeriod.CUSTOM))) {
            if (DateParser.parseDate(budgetDto.getEndDate()).isBefore
                    (DateParser.parseDate(budgetDto.getStartDate())))
                throw new ValidationException("Invalid Date. Please enter a valid date.");
            else {
                budget.setStartDate(DateParser.parseDate(budgetDto.getStartDate()));
                budget.setEndDate(DateParser.parseDate(budgetDto.getEndDate()));
            }
        }

    }
}

