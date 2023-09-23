package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.ContactResponse;
import com.decagon.fitnessoapp.dto.transactionDto.ContactRequest;
import com.decagon.fitnessoapp.model.user.Contact;

import java.util.List;

public interface ContactService {
    ContactRequest save(ContactRequest contactRequest);
    List<Contact> getAllContact();
}
