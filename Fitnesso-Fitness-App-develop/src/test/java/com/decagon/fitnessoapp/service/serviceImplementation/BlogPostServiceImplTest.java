package com.decagon.fitnessoapp.service.serviceImplementation;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.decagon.fitnessoapp.dto.BlogPostResponse;
import com.decagon.fitnessoapp.exception.CustomServiceExceptions;
import com.decagon.fitnessoapp.model.blog.BlogPost;
import com.decagon.fitnessoapp.model.user.Author;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
import com.decagon.fitnessoapp.repository.AuthorRepository;
import com.decagon.fitnessoapp.repository.BlogPostRepository;
import com.decagon.fitnessoapp.repository.PersonRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BlogPostServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BlogPostServiceImplTest {
    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogPostServiceImpl blogPostServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PersonRepository personRepository;

    @Test
    void testGetAllBlogPosts() {
        when(this.modelMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new CustomServiceExceptions("An error occurred"));

        Author author = new Author();
        author.setAuthorName("Charles Dickens");
        author.setBiography("A great writer from orient");
        author.setContact("charles@dickens.com");
        author.setId(123L);
        author.setImage("Young Charles");

        Person person = new Person();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        person.setDateOfBirth(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        person.setEmail("fitness.admin@fitnesso.com");
        person.setFirstName("Admin");
        person.setGender("male");
        person.setId(123L);
        person.setImage("image of admin");
        person.setLastName("Chief");
        person.setPassword("password123");
        person.setPhoneNumber("08036841805");
        person.setResetPasswordToken("ABC123");
        person.setRoleDetail(ROLE_DETAIL.ADMIN);
        person.setUserName("adminus");
        person.setVerifyEmail(true);

        BlogPost blogPost = new BlogPost();
        blogPost.setAuthorName("Anene");
        blogPost.setContent("A long time ago in a far away land...his name is Merlin");
        blogPost.setId(123L);
        blogPost.setPerson(person);
        blogPost.setTitle("Lengends of the Seeker");
        blogPost.setImage("imagine");
        blogPost.setBiography("Being in the business for years");

        ArrayList<BlogPost> blogPostList = new ArrayList<>();
        blogPostList.add(blogPost);
        when(this.blogPostRepository.findAll()).thenReturn(blogPostList);
        assertThrows(CustomServiceExceptions.class, () -> this.blogPostServiceImpl.getAllBlogPosts(1));
        verify(this.modelMapper).map((Object) any(), (Class<Object>) any());
        verify(this.blogPostRepository).findAll();
    }

}

