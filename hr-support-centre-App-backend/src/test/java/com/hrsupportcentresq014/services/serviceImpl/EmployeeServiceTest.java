//package com.hrsupportcentresq014.services.serviceImpl;
//
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.Uploader;
//import com.hrsupportcentresq014.dtos.request.EmployeeProfileRequest;
//import com.hrsupportcentresq014.entities.Employee;
//import com.hrsupportcentresq014.repositories.EmployeeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyMap;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class EmployeeServiceTest {
//
//    @Mock
//    private EmployeeRepository employeeRepository;
//
//    @Mock
//    private Cloudinary cloudinary;
//
//    @InjectMocks
//    private EmployeeServiceImpl employeeService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void updateEmployeeProfile_ValidIdAndRequest_Success() {
//        // Mock data
//        String id = "123";
//        EmployeeProfileRequest request = createEmployeeProfileRequest();
//        Employee existingEmployee = createEmployee();
//        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
//        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);
//
//        // Call the service method
//        EmployeeProfileRequest result = employeeService.updateEmployeeProfile(id, request);
//
//        // Verify the result
//        assertNotNull(result);
//        assertEquals(existingEmployee.getFileName(), result.getFileName());
//        assertEquals(existingEmployee.getEmail(), result.getEmail());
//        assertEquals(existingEmployee.getFileType(), result.getFileType());
//        assertEquals(existingEmployee.getNextOfKin(), result.getNextOfKin());
//        assertEquals(existingEmployee.getMaritalStatus(), result.getMaritalStatus());
//        assertEquals(existingEmployee.getBirthday(), result.getBirthday());
//        assertEquals(existingEmployee.getAddress(), result.getAddress());
//        assertEquals(existingEmployee.getLastUpdated(), result.getLastUpdated());
//    }
//
//    @Test
//    void updateEmployeeProfile_InvalidId_Null() {
//        // Mock data
//        String id = "123";
//        EmployeeProfileRequest request = createEmployeeProfileRequest();
//        when(employeeRepository.findById(id)).thenReturn(Optional.empty());
//
//        // Call the service method
//        EmployeeProfileRequest result = employeeService.updateEmployeeProfile(id, request);
//
//        // Verify the result
//        assertNull(result);
//    }
//
//
//    @Test
//    void uploadDocument_ValidFile_Success() throws Exception {
//        // Mock data
//        MultipartFile multipartFile = createMockMultipartFile();
//        String mockImageUrl = "https://example.com/image.jpg";
//        Cloudinary cloudinaryMock = mock(Cloudinary.class);
//        Uploader uploaderMock = mock(Uploader.class);
//        when(cloudinaryMock.uploader()).thenReturn(uploaderMock);
//        when(uploaderMock.upload(any(byte[].class), anyMap())).thenReturn(Map.of("url", mockImageUrl));
//
//        Mockito.when(cloudinary.uploader()).thenReturn(uploaderMock);
//
//        // Call the service method
//        String result = employeeService.uploadDocument(multipartFile);
//
//        // Verify the result
//        assertNotNull(result);
//        assertEquals(mockImageUrl, result);
//    }
//
//
//    // Helper methods to create mock data
//
//    private EmployeeProfileRequest createEmployeeProfileRequest() {
//        return EmployeeProfileRequest.builder()
//                .fileName("profile.jpg")
//                .email("test@example.com")
//                .fileType("image/jpeg")
//                .nextOfKin("John Doe")
//                .maritalStatus("Single")
//                .birthday(LocalDate.of(1990, 1, 1))
//                .address("123 Main St")
//                .lastUpdated(LocalDate.now())
//                .build();
//    }
//
//    private Employee createEmployee() {
//        Employee employee = new Employee();
//        employee.setFileName("profile.jpg");
//        employee.setEmail("test@example.com");
//        employee.setFileType("image/jpeg");
//        employee.setNextOfKin("John Doe");
//        employee.setMaritalStatus("Single");
//        employee.setBirthday(LocalDate.of(1990, 1, 1));
//        employee.setAddress("123 Main St");
//        employee.setLastUpdated(LocalDate.now());
//        return employee;
//    }
//
//    private MockMultipartFile createMockMultipartFile() {
//        return new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{});
//    }
//}