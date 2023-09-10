package com.hrsupportcentresq014.dtos.response;

import com.hrsupportcentresq014.entities.Employee;
import org.modelmapper.ModelMapper;

public class StaffProfileMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static StaffProfileDTO mapEmployeeToStaffProfileDTO(Employee employee) {
        return modelMapper.map(employee, StaffProfileDTO.class);
    }





}

