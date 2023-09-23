package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.transactionDto.ContactRequest;
import com.decagon.fitnessoapp.model.user.Contact;
import com.decagon.fitnessoapp.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/save")
    public ResponseEntity<ContactRequest> saveContact(@Valid @RequestBody ContactRequest contactRequest){
        return ResponseEntity.ok(contactService.save(contactRequest));
    }

    @GetMapping("/getContacts")
    public ResponseEntity<List<Contact>> getAllContacts(){
        return ResponseEntity.ok(contactService.getAllContact());
    }
}
