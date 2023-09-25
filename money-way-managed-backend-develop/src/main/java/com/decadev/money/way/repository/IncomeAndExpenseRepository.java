package com.decadev.money.way.repository;

import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.model.IncomeAndExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeAndExpenseRepository extends JpaRepository<IncomeAndExpense, Long> {

   @Query(value = "SELECT * FROM income_and_expense WHERE EXTRACT(YEAR FROM date) =:year AND EXTRACT(MONTH FROM " +
           "date)=:month And EXTRACT(DAY FROM date)>=:starting AND EXTRACT(DAY FROM date) <= :ending AND user_id=:userId AND category=:category", nativeQuery = true)
    List<IncomeAndExpense> findIncomeAndExpenseByUserIdAndDate(@Param("year") int year,@Param("month") int month,@Param("starting") int starting,
                                                               @Param("ending") int ending, @Param("userId") Long userId, @Param("category") String category );


   List<IncomeAndExpense> findIncomeAndExpenseByUserIdAndYearAndCategoryOrderByDateAsc(Long userId, int year, TransactionCategory category);
}
