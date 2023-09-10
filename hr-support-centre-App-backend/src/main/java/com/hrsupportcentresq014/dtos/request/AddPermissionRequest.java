package com.hrsupportcentresq014.dtos.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddPermissionRequest {
    private String permission;
}
