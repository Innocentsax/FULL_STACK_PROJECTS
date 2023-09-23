package com.example.hive.utils;

import com.example.hive.dto.request.EmailDto;
import com.example.hive.entity.Task;

import com.example.hive.entity.User;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EmailTemplates {

    private static final String senderCredential = "hive@blessingchuks.tech";

    public static EmailDto createVerificationEmail(User recipient, String token, String eventUrl) {
        String verificationUrl = eventUrl
                + "/verifyRegistration?token="
                + token;

        String mailContent = "<p> Dear "+ recipient.getFullName() +", </p>";
        mailContent += "<p> Please click the link below to verify your registration with the link below, </p>";
        mailContent += "<h3><a href=\""+ verificationUrl + "\"> VERIFICATION LINK </a></h3>";
        mailContent += "<p>Thank you <br/> Hive team </p>";


        log.info("Link created {}", verificationUrl);
        return EmailDto.builder()
                .sender(senderCredential)
                .subject("Please Activate Your Account")
                .body(mailContent)
                .recipient(recipient.getEmail())
                .build();


    }

    public static EmailDto taskCreationNotificationEmail(User recipient, Task task, String eventUrl) {
        String taskUrl = eventUrl + "/tasks/" + task.getTask_id();

        String mailContent = "<p> Dear " + recipient.getFullName() + ", </p>";
        mailContent += "<p> You have created a new task with the following details: </p>";
        mailContent += "<ul>";
        mailContent += "<li>Job Type: " + task.getJobType() + "</li>";
        mailContent += "<li>Task Description: " + task.getTaskDescription() + "</li>";
        mailContent += "</ul>";
        mailContent += "<p> You can view your task details at the following URL: </p>";
        mailContent += "<h3><a href=\"" + taskUrl + "\">" + taskUrl + "</a></h3>";
        mailContent += "<p>Thank you <br/> Hive team </p>";

        log.info("Task notification for {} has been sent", recipient.getEmail());
        return EmailDto.builder()
                .sender(senderCredential)
                .subject("Task Confirmation")
                .body(mailContent)
                .recipient(recipient.getEmail())
                .build();
    }

    public static EmailDto createPaymentVerificationCodeMail(User recipient, String reference) {

        String mailContent = "<p> Dear \"" + recipient.getFullName() +  "\", your Verification code is \"" + reference + "\"</p>";

        return EmailDto.builder()
                .sender(senderCredential)
                .subject("Payment Verification Code")
                .body(mailContent)
                .recipient(recipient.getEmail())
                .build();
    }

    public static EmailDto createSuccessfulPaymentFromTaskerEmail(User recipient, String taskTitle) {

        String mailContent = "<p> Dear \"" + recipient.getFullName() +  "\", your payment for for the task with description below was successful: \n \"" + taskTitle + "\" </p>";

        return EmailDto.builder()
                .sender(senderCredential)
                .subject("Payment Successful")
                .body(mailContent)
                .recipient(recipient.getEmail())
                .build();
    }

    public static EmailDto createSuccessfulCreditEmail(User doer, String taskTitle) {

            String mailContent = "<p> Dear \"" + doer.getFullName() +  "\", your wallet was successfully credited for the task with description: \n \"" + taskTitle + "</p>";

            return EmailDto.builder()
                    .sender(senderCredential)
                    .subject("Wallet Credited")
                    .body(mailContent)
                    .recipient(doer.getEmail())
                    .build();
    }
}
