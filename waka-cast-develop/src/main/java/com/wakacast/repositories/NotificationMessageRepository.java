package com.wakacast.repositories;

import com.wakacast.models.NotificationMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {
    Page<NotificationMessage> findNotificationMessageBySenderEmail(String email, Pageable pageable);
    Page<NotificationMessage> findNotificationMessageByReceiverEmail(String email, Pageable pageable);

    @Query(value = "SELECT * FROM public.notification_message nm WHERE nm.sender_email = :email " +
            "AND nm.receiver_email = :otherEmail OR nm.sender_email = :otherEmail " +
            "AND nm.receiver_email = :email ORDER BY create_date ASC ", nativeQuery = true)
    Page<NotificationMessage> findBothUserMessage(
            @Param("email") String email, @Param("otherEmail") String otherEmail, Pageable pageable);
}
