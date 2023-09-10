package com.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.MailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private MailService mailService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createHr() throws MessagingException {
        CreateHrResponseDTO hrRequestDTO = new CreateHrResponseDTO();
        hrRequestDTO.setFirstName("John");
        hrRequestDTO.setLastName("Doe");
        hrRequestDTO.setEmail("john.doe@example.com");


        Employee existingEmployee = new Employee();
        existingEmployee.setEmail(hrRequestDTO.getEmail());

        when(employeeRepository.findByEmail(hrRequestDTO.getEmail())).thenReturn(Optional.of(existingEmployee));
        when(roleRepository.findRoleById("hr")).thenReturn(Optional.of(new Role())); // Replace with the actual Role class

        // Perform the test
        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.createHr(hrRequestDTO));

        // Verify the results
        assertEquals("User already exists", exception.getMessage());
        verify(mailService, never()).sendAccountActivation(any(), any()); // Verify that mailService.sendAccountActivation was not called
        verify(employeeRepository, never()).save(any(Employee.class)); // Verify that employeeRepository.save was not called
    }
}