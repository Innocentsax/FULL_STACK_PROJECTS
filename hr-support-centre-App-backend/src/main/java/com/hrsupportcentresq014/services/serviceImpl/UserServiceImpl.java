package com.hrsupportcentresq014.services.serviceImpl;


import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import com.hrsupportcentresq014.entities.Employee;
import com.hrsupportcentresq014.entities.Role;
import com.hrsupportcentresq014.exceptions.EmployeeExistsException;
import com.hrsupportcentresq014.exceptions.InvalidDateException;
import com.hrsupportcentresq014.exceptions.NoRoleFoundException;
import com.hrsupportcentresq014.repositories.EmployeeRepository;
import com.hrsupportcentresq014.repositories.RoleRepository;
import com.hrsupportcentresq014.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final Mapper mapper;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    @Override
    public boolean isValidDateOfBirth(String dateOfBirth){
        LocalDate dob = LocalDate.parse(dateOfBirth);
        LocalDate now = LocalDate.now();
        LocalDate oldestDateOfBirth = now.minusYears(120);
        return dob.isBefore(now) && dob.isAfter(oldestDateOfBirth);
    }

    //register admin
    @Override
    public AdminResponse register(AdminRequest adminRequest) {
        if(employeeRepository.findByEmail(adminRequest.getEmail()).isPresent()){
            throw new EmployeeExistsException("employee with " + adminRequest.getEmail() + " already exists");
        }
        if(!isValidDateOfBirth(adminRequest.getDateOfBirth())) {
            log.error("Check that Date of Birth is not in the future or far into the past");
            throw new InvalidDateException("invalid: date of birth is not valid. ");
        }
        Optional<Role> adminRole = roleRepository.findRoleById("admin");
        if(adminRole.isPresent()) {
            Employee employee = mapper.toEntity(adminRequest);
            employee.setRole(adminRole.get());
            Employee registeredEmployee = employeeRepository.save(employee);
            log.info("employee saved {}", registeredEmployee);
            return mapper.toResponse(registeredEmployee);
        } else {
            log.warn("Admin role not found");
            throw new NoRoleFoundException("role not found");
        }
    }
}
