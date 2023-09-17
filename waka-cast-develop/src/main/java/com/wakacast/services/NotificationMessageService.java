package com.wakacast.services;

import com.wakacast.dto.NotificationMessageDto;
import com.wakacast.dto.pages_criteria.NotificationPage;
import com.wakacast.dto.response_dtos.NotificationMessageResponseDto;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface NotificationMessageService {
    String sendMessageTo(NotificationMessageDto notificationMessageDto) throws IOException;
    Page<NotificationMessageResponseDto> getAllSentMessages (NotificationPage notificationPage);
    Page<NotificationMessageResponseDto> getAllReceivedMessages (NotificationPage notificationPage);
    Page<NotificationMessageResponseDto> getBothUserMessages (NotificationPage notificationPage, Long id);
}
