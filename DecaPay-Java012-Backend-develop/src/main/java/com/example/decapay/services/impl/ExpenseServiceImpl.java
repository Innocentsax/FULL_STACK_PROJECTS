package com.example.decapay.services.impl;

import com.example.decapay.exceptions.NotFoundException;
import com.example.decapay.exceptions.ResourceNotFoundException;
import com.example.decapay.models.Expense;
import com.example.decapay.models.LineItem;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.repositories.ExpenseRepository;
import com.example.decapay.repositories.LineItemRepository;
import com.example.decapay.repositories.UserRepository;
import com.example.decapay.services.ExpenseService;
import com.example.decapay.utils.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final LineItemRepository lineItemRepository;
    private final UserRepository userRepository;


    @Override
    public ResponseEntity<ExpenseResponseDto> createExpense(ExpenseRequestDto expenseRequestDto, Long lineId) {

        LineItem lineItem = lineItemRepository.findById(lineId).orElseThrow(() -> new ResourceNotFoundException("Line item does not exist", HttpStatus.NOT_FOUND, "Please select a valid line item"));
        BigDecimal totalAmount = lineItem.getTotalAmountSpent();


        Expense expense = new Expense();
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDescription(expenseRequestDto.getDescription());
        expense.setLineItem(lineItem);
        expenseRepository.save(expense);


        BigDecimal newTotal = totalAmount.add(expense.getAmount());
        lineItem.setTotalAmountSpent(newTotal);
        lineItemRepository.save(lineItem);


        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setCreatedAt(expense.getCreatedAt());

        return ResponseEntity.ok(expenseResponseDto);
    }


    @Override
    public Boolean deleteExpense(Long id){

        Expense expense=expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found",HttpStatus.BAD_REQUEST,"Please select a valid Expense"));

        LineItem lineItem = expense.getLineItem();
        BigDecimal totalAmount = lineItem.getTotalAmountSpent();
        BigDecimal preAmount = totalAmount.subtract(expense.getAmount());

        expenseRepository.delete(expense);

        lineItem.setTotalAmountSpent(preAmount);
        lineItemRepository.save(lineItem);

        return true;

    }

    @Override
    public ResponseEntity<Page<Expense>> getExpenses(Long lineId, Integer pageNo, Integer pageSize, String sortBy, boolean isAscending) {
        Page<Expense> userPage = expenseRepository.findAllLineItemById(lineId, PageRequest.of(pageNo, pageSize,
                isAscending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy ));
        return ResponseEntity.ok(userPage);
    }

    @Override
    public ExpenseResponseDto updateExpense(ExpenseRequestDto expenseRequestDto, Long expenseId) {

        Expense expense = expenseRepository.findById(expenseId).orElseThrow(() ->
        { throw new NotFoundException("Expense not found");});

        LineItem lineItem = expense.getLineItem();
        BigDecimal totalAmount = lineItem.getTotalAmountSpent();
        BigDecimal preAmount = totalAmount.subtract(expense.getAmount());

        expense.setAmount(expenseRequestDto.getAmount());
        expense.setDescription(expenseRequestDto.getDescription());
        expense = expenseRepository.save(expense);

        lineItem.setTotalAmountSpent(preAmount.add(expense.getAmount()));
        lineItemRepository.save(lineItem);

        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());
        expenseResponseDto.setDescription(expense.getDescription());
        expenseResponseDto.setCreatedAt(expense.getCreatedAt());
        return expenseResponseDto;

    }
}
