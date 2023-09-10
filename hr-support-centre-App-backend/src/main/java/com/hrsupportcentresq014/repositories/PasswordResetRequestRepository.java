package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.PasswordResetRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordResetRequestRepository extends MongoRepository<PasswordResetRequest, String> {
    PasswordResetRequest findPasswordResetRequestByResetToken(String resetToken);
}