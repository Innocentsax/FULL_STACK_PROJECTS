package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class ImageUrl extends Base{
    @NotNull(message = "imageUrl isempty")
    private String img;
}
