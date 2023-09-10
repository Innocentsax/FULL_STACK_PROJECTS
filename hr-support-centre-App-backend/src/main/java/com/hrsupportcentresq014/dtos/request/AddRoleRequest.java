package com.hrsupportcentresq014.dtos.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AddRoleRequest {
    private String id;
    private String name;
}
