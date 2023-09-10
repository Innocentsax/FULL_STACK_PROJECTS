package com.hrsupportcentresq014.dtos.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ViewStaffResponseDTO {
    private String fullName;
    private String position;
    private String email;
    private  String phoneNo;
    private String department;
    private String contractType;
    private  String status = "Worker";
    private Long tenure;


}
