package com.example.decapay.controllers;

import com.example.decapay.models.Expense;
import com.example.decapay.pojos.expenseDto.ExpenseRequestDto;
import com.example.decapay.pojos.expenseDto.ExpenseResponseDto;
import com.example.decapay.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    @PostMapping("/create-expense/{lineId}")
    public ResponseEntity<ExpenseResponseDto> createExpense(@RequestBody @Valid ExpenseRequestDto expenseRequestDto, @PathVariable Long lineId){
        return expenseService.createExpense(expenseRequestDto,lineId);
    }


    @DeleteMapping("/delete_expense/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable Long id){
        return new ResponseEntity<>( expenseService.deleteExpense(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{lineId}/expenses")
    public ResponseEntity<Page<Expense>> getExpenses(@PathVariable Long lineId,
                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "false")boolean isAscending) {
                return expenseService.getExpenses(lineId,pageNo,pageSize,sortBy, isAscending);
    }

    @PutMapping("/update/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(@Valid @RequestBody ExpenseRequestDto expenseRequestDto,
                                                            @PathVariable Long expenseId ){
        return new ResponseEntity<>(expenseService.updateExpense(expenseRequestDto, expenseId), HttpStatus.OK);
    }
}
