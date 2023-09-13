package com.decagon.adire.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String id;

    //    @NotBlank(message = "email cannot be empty")
    private String customerEmail;
    //    @NotBlank
    private String materialType;
    //    @NotBlank
    private String duration;
    //    @NotBlank
    private String designImageUrl;
    //    @NotNull
    private Double orderPrice;
    //    @NotBlank
    private LocalDateTime dueDate;
    private List<MeasurementDto> measurementList;
    private String orderStatus;
}

