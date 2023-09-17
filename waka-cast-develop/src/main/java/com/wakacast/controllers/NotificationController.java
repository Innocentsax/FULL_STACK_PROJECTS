package com.wakacast.controllers;

import com.wakacast.dto.NotificationMessageDto;
import com.wakacast.dto.pages_criteria.NotificationPage;
import com.wakacast.dto.response_dtos.NotificationMessageResponseDto;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.NotificationMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static com.wakacast.controllers.UserController.response;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationMessageService notificationMessageService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/send-notification")
    public ResponseEntity<ResponseData> sentMessage(@Valid @RequestBody NotificationMessageDto notificationMessageDto) throws IOException {
        return response(HttpStatus.OK, notificationMessageService.sendMessageTo(notificationMessageDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-sent-messages")
    public ResponseEntity<Page<NotificationMessageResponseDto>> getAllSentNotifications(NotificationPage notificationPage) {
        return new ResponseEntity<>(notificationMessageService.getAllSentMessages(notificationPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-received-messages")
    public ResponseEntity<Page<NotificationMessageResponseDto>> getAllReceivedNotifications(NotificationPage notificationPage) {
        return new ResponseEntity<>(notificationMessageService.getAllReceivedMessages(notificationPage),  HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/get-both-user-message/{id}")
    public ResponseEntity<Page<NotificationMessageResponseDto>> getBothUserMessages(NotificationPage notificationPage, @PathVariable Long id) {
        return new ResponseEntity<>(notificationMessageService.getBothUserMessages(notificationPage, id),  HttpStatus.OK);
    }
}
