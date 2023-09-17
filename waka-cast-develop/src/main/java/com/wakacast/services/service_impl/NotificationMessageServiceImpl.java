package com.wakacast.services.service_impl;

import com.wakacast.dto.NotificationMessageDto;
import com.wakacast.dto.pages_criteria.NotificationPage;
import com.wakacast.dto.response_dtos.NotificationMessageResponseDto;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.exceptions.UserWithIdNotFound;
import com.wakacast.models.NotificationMessage;
import com.wakacast.models.User;
import com.wakacast.repositories.NotificationMessageRepository;
import com.wakacast.repositories.UserRepository;
import com.wakacast.requests.EmailRequest;
import com.wakacast.services.NotificationMessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.MessagingException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationMessageServiceImpl implements NotificationMessageService {

    private final UserRepository userRepository;
    private final NotificationMessageRepository notificationMessageRepository;
    private final ModelMapper mapper;
    private final EmailService emailService;

    @Override
    public String sendMessageTo(NotificationMessageDto notificationMessageDto) throws IOException{
        String msg = "";
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();

        User sender = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound("User with " + email + " was not found"));

        User receiver = userRepository.findUserByEmail(notificationMessageDto.getReceiverEmail()).orElseThrow(() ->
                new UserWithEmailNotFound("User you wish to send message to is not a wakacast app user"));

        NotificationMessage notificationMessageToSend = mapper.map(notificationMessageDto, NotificationMessage.class);

        notificationMessageToSend.setSenderEmail(sender.getEmail());
        notificationMessageToSend.setReceiverEmail(receiver.getEmail());

        try {
            notificationMessageRepository.save(notificationMessageToSend);
            EmailRequest applicationMailNotification = new EmailRequest();
            applicationMailNotification.setTo(receiver.getEmail());
            applicationMailNotification.setSubject("NOTIFICATION FROM " + sender.getFirstName() +" "+sender.getSurname());
            applicationMailNotification.setBody("You have a very important message on your WAKACAST platform." +
                    " Log in to your account to read the message");
            emailService.sendEmail(applicationMailNotification);
            msg = "Your notification was sent successfully.";
        } catch (MessagingException e) {
            msg = "Sorry, notification was not send. ";
            throw new BadRequestException(msg + e.getMessage());
        }

        return msg;
    }


    @Override
    public Page<NotificationMessageResponseDto> getAllSentMessages(NotificationPage notificationPage) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        Sort sort = Sort.by(notificationPage.getSortDirection(), notificationPage.getSortBy());
        Pageable pageable = PageRequest.of(notificationPage.getPageNumber(), notificationPage.getPageSize(), sort);
        Page<NotificationMessage> notificationMessages = notificationMessageRepository.findNotificationMessageBySenderEmail(email, pageable);

        return notificationMessages.map(message -> mapper.map(message, NotificationMessageResponseDto.class));
    }


    @Override
    public Page<NotificationMessageResponseDto> getAllReceivedMessages(NotificationPage notificationPage) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        Sort sort = Sort.by(notificationPage.getSortDirection(), notificationPage.getSortBy());
        Pageable pageable = PageRequest.of(notificationPage.getPageNumber(), notificationPage.getPageSize(), sort);
        Page<NotificationMessage> notificationMessage = notificationMessageRepository.findNotificationMessageByReceiverEmail(email, pageable);
        return notificationMessage.map(message -> mapper.map(message, NotificationMessageResponseDto.class));
    }

    @Override
    public Page<NotificationMessageResponseDto> getBothUserMessages(NotificationPage notificationPage, Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User otherUser = userRepository.findById(id).orElseThrow(() ->new UserWithIdNotFound("User not found"));
        Pageable pageable = PageRequest.of(notificationPage.getPageNumber(), notificationPage.getPageSize());
        Page<NotificationMessage> bothUserMessage = notificationMessageRepository
                .findBothUserMessage(userDetails.getUsername(), otherUser.getEmail(), pageable);

        return bothUserMessage.map(message -> mapper.map(message, NotificationMessageResponseDto.class));
    }
}
