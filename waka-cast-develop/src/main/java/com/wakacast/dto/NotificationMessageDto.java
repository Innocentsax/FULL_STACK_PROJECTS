package com.wakacast.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NotificationMessageDto{
    @NotNull
    @NotBlank(message = "This is a required field")
    String receiverEmail;
    @NotNull
    @NotBlank(message = "This is a required field")
    String content;
}
