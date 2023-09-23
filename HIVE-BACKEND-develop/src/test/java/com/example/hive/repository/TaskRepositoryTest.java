package com.example.hive.repository;

import com.example.hive.entity.Task;
import com.example.hive.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryTest {
    @Mock
    TaskRepository taskRepository;


    private Task task2;

    @BeforeEach
    public void setUp() {
        task2 = new Task();
        task2.setTask_id(UUID.randomUUID());
        task2.setJobType("Logistics");
        task2.setTaskDescription("Get me food from The Place");
        task2.setBudgetRate(new BigDecimal("5000"));
        task2.setTaskAddress("The Place Restaurant");
        task2.setTaskDeliveryAddress("7 Asajon Way, Sangotedo");
        task2.setEstimatedTime(1);
        task2.setTaskDuration(LocalDateTime.now());
        task2.setStatus(Status.COMPLETED);
    }

    //    }
    @Test
    void getAllTasks() {
        List<Task> result = taskRepository.findAll();
        verify(taskRepository).findAll();
        assertEquals(0, result.size());

    }
    @Test
    void getTask() {
        // Arrange
        UUID taskId = UUID.randomUUID();
        Task expectedTask = new Task();
        expectedTask.setTask_id(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

        // Act
        Optional<Task> result = taskRepository.findById(taskId);

        // Assert
        verify(taskRepository, times(1)).findById(taskId);
        assertTrue(result.isPresent());
        assertEquals(expectedTask, result.get());
    }


    @Test
    void searchTasksBy() {

        // Arrange

            // Mock the repository to return some sample tasks
            List<Task> tasks = Arrays.asList(task2);
        when(taskRepository.searchTasksBy(anyString())).thenReturn(Optional.of(tasks));

            // Call the service method to search for tasks
            Optional<List<Task>> result = taskRepository.searchTasksBy("Logistics");

            // Verify that the repository method was called with the correct parameter
        verify(taskRepository).searchTasksBy("Logistics");

            // Verify that the service method returned the expected result
            assertTrue(result.isPresent());
            assertEquals(1, result.get().size());
            assertTrue(result.get().contains(task2));

    }
}