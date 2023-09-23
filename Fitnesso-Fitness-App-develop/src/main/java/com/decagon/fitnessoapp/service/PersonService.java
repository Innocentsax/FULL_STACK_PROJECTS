package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.*;
//import com.mailjet.client.errors.MailjetException;
//import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.TrainerProfile;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;

public interface PersonService {

    ResponseEntity<AuthResponse> loginUser(AuthRequest req) throws Exception;

    PersonInfoResponse getInfo(Authentication authentication) throws Exception;

    UpdatePersonResponse updateUserDetails(UpdatePersonRequest updatePersonRequest);

    PersonResponse register(PersonRequest personRequest) throws IOException;

    PersonResponse addTrainer(PersonRequest personRequest);

    TrainerResponse addTrainerModel(TrainerRequest request);

    List<TrainerProfile> getTrainers();

    ResponseEntity<String> removeTrainer(Long id);

    PersonResponse sendingEmail(String email) ;

    AdminStats getFitnessoDetails();

    ChangePasswordResponse updateCurrentPassword(ChangePasswordRequest changePasswordRequest);

    PersonResponse resetPasswordToken(String email) ;

    PersonResponse updateResetPassword(ResetPasswordRequest passwordRequest, String token);

    void resetPasswordMailSender(String email, String token) ;

    String buildEmail(String name, String link);

    Page<Person> getAllUsers(int pageNumber);

}
