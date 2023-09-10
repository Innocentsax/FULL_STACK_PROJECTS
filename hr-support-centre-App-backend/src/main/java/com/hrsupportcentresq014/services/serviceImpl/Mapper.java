package com.hrsupportcentresq014.services.serviceImpl;




import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.request.JobPostingRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import com.hrsupportcentresq014.dtos.response.JobPostingResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;




@Component
@RequiredArgsConstructor
public class Mapper {
    private final PasswordEncoder passwordEncoder;


    //map to entity
    public Employee toEntity(AdminRequest adminRequest){
        return Employee.builder()
                .birthday(LocalDate.parse(adminRequest.getDateOfBirth().trim()))
                .phoneNo(adminRequest.getPhoneNumber().trim())
                .position("Manager")
                .firstName(adminRequest.getFirstName().trim())
                .nickName(adminRequest.getNickName().trim())
                .lastName(adminRequest.getLastName().trim())
                .password(passwordEncoder.encode(adminRequest.getPassword().trim()))
                .email(adminRequest.getEmail().trim())
                .build();
    }
    //map to response
    public AdminResponse toResponse(Employee employee){
        return AdminResponse.builder()
                .firstName(employee.getFirstName())
                .nickName(employee.getNickName())
                .lastName(employee.getLastName())
                .dateOfBirth(employee.getBirthday().toString())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNo())
                .role(employee.getRole())
                .position(employee.getPosition())
                .build();
    }


    public JobPostingResponse jobToResponse(Job job){
        return JobPostingResponse.builder()
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .closingDate(job.getClosingDate())
                .departmentName(job.getDepartmentName())
                .isActive(job.getIsActive())
                .build();
    }


    public Job requestToJob(JobPostingRequest request){
        return Job.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .departmentName(request.getDepartmentName())
                .requirements(request.getRequirements())
                .closingDate(request.getClosingDate())
                .build();
    }
}


