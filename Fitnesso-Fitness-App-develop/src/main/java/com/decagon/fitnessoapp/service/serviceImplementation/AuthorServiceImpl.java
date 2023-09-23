package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.AuthorRequest;
import com.decagon.fitnessoapp.dto.AuthorResponse;
import com.decagon.fitnessoapp.model.user.Author;
import com.decagon.fitnessoapp.repository.AuthorRepository;
import com.decagon.fitnessoapp.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorResponse saveAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setAuthorName(request.getAuthorName());
        author.setImage(request.getImage());
        author.setContact(request.getContact());
        author.setBiography(request.getBiography());
        authorRepository.save(author);
        return modelMapper.map(author, AuthorResponse.class);
    }
}
