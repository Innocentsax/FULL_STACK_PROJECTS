package com.example.money_way.repository;

import com.example.money_way.dto.response.FinancialSummaryResponseDto;
import com.example.money_way.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByVirtualAccountRef(String ref);

    Optional<Transaction> findByUserIdAndTransactionId(Long userId, Long transactionId);

    Optional<Transaction> findByTransactionId(Long transactionId);
    @Query(value = "SELECT * FROM transaction_tbl t WHERE (t.user_id = :userId AND t.created_at BETWEEN cast(:startDate as Date) AND cast(:endDate as Date) + interval '1' day) ORDER BY t.created_at DESC LIMIT :limit_ OFFSET :offset_", nativeQuery = true)
    List<Transaction> findAllByUserId(Long userId, @Param("limit_") Integer limit,
                                      @Param("offset_") Integer offset,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);
    @Query(value = "SELECT SUM(amount) AS total_amount FROM transaction_tbl WHERE payment_type <> 'deposits' AND EXTRACT(MONTH FROM created_at) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM created_at) = EXTRACT(YEAR FROM CURRENT_DATE);", nativeQuery = true)
    BigDecimal findTotalExpenseTransaction();


    @Query(value = "SELECT EXTRACT(MONTH FROM created_at) AS month,"
            + " SUM(CASE WHEN payment_Type = 'Deposits' THEN amount ELSE 0 END) AS deposits_sum,"
            + " SUM(CASE WHEN payment_Type <> 'Deposits' THEN amount ELSE 0 END) AS expense"
            + " FROM public.transaction_tbl"
            + " GROUP BY month"
            + " ORDER BY month DESC", nativeQuery = true)
    List<FinancialSummaryResponseDto> getTransactionsByMonth(@Param("userId") Long userId);
}
