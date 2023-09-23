package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.ContactResponse;
import com.decagon.fitnessoapp.dto.transactionDto.ContactRequest;
import com.decagon.fitnessoapp.model.user.Contact;
import com.decagon.fitnessoapp.repository.ContactRepository;
import com.decagon.fitnessoapp.service.ContactService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public ContactRequest save(ContactRequest contactRequest) {
        Contact contact = new Contact();
        modelMapper.map(contactRequest, contact);
        contactRepository.save(contact);
        return contactRequest;
    }

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }
}
