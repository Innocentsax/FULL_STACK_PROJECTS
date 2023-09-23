package com.example.hive.service;



import com.example.hive.dto.request.EmailDto;

import java.io.IOException;

public interface EmailService {

    void sendEmail(EmailDto emailDto) throws IOException;

}