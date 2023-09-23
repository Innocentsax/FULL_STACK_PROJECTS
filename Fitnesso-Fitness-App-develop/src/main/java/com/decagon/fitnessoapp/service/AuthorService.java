package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.AuthorRequest;
import com.decagon.fitnessoapp.dto.AuthorResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
AuthorResponse saveAuthor(AuthorRequest request);
}
