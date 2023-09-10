package com.hrsupportcentresq014.entities;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.repository.Update;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class View extends BaseEntity{

    private String employeeId;
    private boolean hasView;
    @CreatedDate
    private LocalDateTime dateView;

}
