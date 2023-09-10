package com.hrsupportcentresq014.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    public static final String ADMIN_CREATE = "ADMIN_CREATE";
    public static final String ADMIN_READ = "ADMIN_READ";
    public static final String ADMIN_UPDATE = "ADMIN_UPDATE";
    public static final String ADMIN_DELETE = "ADMIN_DELETE";
    public static final String HR_CREATE = "HR_CREATE";
    public static final String HR_READ = "HR_READ";
    public static final String HR_UPDATE = "HR_UPDATE";
    public static final String HR_DELETE = "HR_DELETE";

    private String permission;
}
