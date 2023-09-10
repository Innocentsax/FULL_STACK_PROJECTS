package com.hrsupportcentresq014.controllers;


import com.hrsupportcentresq014.dtos.request.AddPermissionRequest;
import com.hrsupportcentresq014.dtos.request.AddRoleRequest;
import com.hrsupportcentresq014.dtos.request.AdminRequest;
import com.hrsupportcentresq014.dtos.response.AdminResponse;
import com.hrsupportcentresq014.dtos.response.CreateHrResponseDTO;
import com.hrsupportcentresq014.exceptions.UserAlreadyExistsException;
import com.hrsupportcentresq014.services.EmployeeService;
import com.hrsupportcentresq014.services.RoleService;
import com.hrsupportcentresq014.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final EmployeeService employeeService;
    @PostMapping(name = "RegisterAdmin", value = "/register")
    public ResponseEntity<AdminResponse> registerAdmin(@Valid @RequestBody AdminRequest adminRequest){
        log.info("Registering Admin with payload {}", adminRequest);
        AdminResponse adminResponse = userService.register(adminRequest);
        return new ResponseEntity<>(adminResponse, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-roles")
    public ResponseEntity<?> addRoles (@RequestBody AddRoleRequest addRole){
        return new ResponseEntity<>(roleService.addRoles(addRole), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/add-role-permissions/{role_id}")
    public ResponseEntity<?> addPermissions (@RequestBody AddPermissionRequest request, @PathVariable String role_id)
    {
        return new ResponseEntity<>(roleService.addPermission(request, role_id), HttpStatus.CREATED);
    }

    @PostMapping(name =  "RegisterHr", value= "/register-hr" )
    public ResponseEntity<CreateHrResponseDTO> createHr(@RequestBody CreateHrResponseDTO hrDTO) throws UserAlreadyExistsException {
        return new ResponseEntity<>(employeeService.createHr(hrDTO), HttpStatus.CREATED);
    }
}
