package com.wakacast.models;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationMessage extends BaseClass {
    String senderEmail;
    String receiverEmail;
    String content;
}
