package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DepartmentRepository extends MongoRepository<Department, String> {


    Optional<Department> findDepartmentByDepartmentName(String departmentName);
}
