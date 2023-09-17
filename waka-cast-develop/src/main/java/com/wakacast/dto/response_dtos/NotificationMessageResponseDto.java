package com.wakacast.dto.response_dtos;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class NotificationMessageResponseDto {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    String receiverEmail;
    String senderEmail;
    String content;
}
