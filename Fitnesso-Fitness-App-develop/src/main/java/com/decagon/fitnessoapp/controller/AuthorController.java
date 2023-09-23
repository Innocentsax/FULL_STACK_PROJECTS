package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.AuthorRequest;
import com.decagon.fitnessoapp.dto.AuthorResponse;
import com.decagon.fitnessoapp.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
@CrossOrigin
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/save_author")
    public AuthorResponse addAuthor(@RequestBody AuthorRequest request){
        return authorService.saveAuthor(request);
    }
}
