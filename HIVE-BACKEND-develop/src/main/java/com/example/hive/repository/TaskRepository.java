package com.example.hive.repository;
import com.example.hive.entity.Task;
import com.example.hive.entity.User;
import com.example.hive.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByTaskDescription(String taskDescription);

    List<Task> findAll();

    @Query(value = "SELECT * from tasks t WHERE t.job_type LIKE ?1 or t.task_address LIKE ?1 or " +
            "t.task_delivery_address LIKE ?1 or t.task_description LIKE ?1" , nativeQuery = true)
    Optional<List<Task>> searchTasksBy(@RequestParam("query") String text);


    Optional<Task> findByJobTypeAndTaskDescriptionAndBudgetRateAndTaskAddressAndTaskDeliveryAddressAndEstimatedTimeAndStatus(String jobType, String taskDescription, BigDecimal budgetRate, String taskAddress, String taskDeliveryAddress, Integer estimatedTime, Status status);

    @Query("SELECT t FROM Task t WHERE t.doer = :doer AND t.status = 'ONGOING'")
    List<Task> findOngoingTasksByDoer(@Param("doer") User doer);

    @Query("SELECT t FROM Task t WHERE t.doer = :doer AND t.status = 'COMPLETED'")
    List<Task> findCompletedTasksByDoer(@Param("doer") User doer);

    List<Task> findAllByTaskerAndStatus(User currentUser, Status valueOf);

    List<Task> findAllByTaskDurationLessThanAndStatus(LocalDateTime now, Status status);

    List<Task> findAllByDoerAndStatus(User currentUser, Status valueOf);
}

