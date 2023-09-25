package com.decadev.money.way.repository;


import com.decadev.money.way.dto.transaction.AmountPerWeek;
import com.decadev.money.way.enums.TransactionCategory;
import com.decadev.money.way.model.Transaction;

import com.decadev.money.way.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
public interface TransactionRepository  extends JpaRepository<Transaction, Long> {


//    @Query(value = "SELECT SUM(CAST(amount AS DECIMAL)) FROM transaction t", nativeQuery = true)
//    BigDecimal findTransactionSum( );

    @Query(value = "SELECT SUM(CAST(amount AS DECIMAL)) AS sumAmount " +
            "FROM transaction " +
            "WHERE EXTRACT(YEAR FROM date) = :year AND EXTRACT(MONTH FROM date) = :month " +
            "AND EXTRACT(DAY FROM date) >= :startDay AND EXTRACT(DAY FROM date) <= :endDay AND users_id=:userId and category=:category", nativeQuery = true)
    BigDecimal findTransactionSumByDays(@Param("year") int year, @Param("month") int month,
                                        @Param("startDay") int startDay, @Param("endDay") int endDay, @Param("userId") Long userId, @Param("category") String category);





  @Query(value = "SELECT * FROM transaction t WHERE (t.users_id = :userId AND t.date BETWEEN cast(:startDate as Date) AND cast(:endDate as Date) + interval '1' day"
          + " AND (:minAmount IS NULL OR cast(t.amount as numeric) >= cast(:minAmount as numeric)) AND (:maxAmount IS NULL OR cast(t.amount as numeric) <= cast(:maxAmount as numeric)))", nativeQuery = true)
  Page<Transaction> findAllByUserIdAndAmountRangeOptional(
          @Param("userId") Long userId,
          @Param("startDate") String startDate,
          @Param("endDate") String endDate,
          @Param("minAmount") BigDecimal minAmount,
          @Param("maxAmount") BigDecimal maxAmount,
          Pageable pageable);

Optional<Transaction> findTransactionByIdAndUserId(Long transactionID, Long userID);


}
