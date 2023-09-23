package com.decagon.fitnessoapp.blogpost;

import com.decagon.fitnessoapp.controller.BlogPostController;
import com.decagon.fitnessoapp.dto.BlogPostResponse;
import com.decagon.fitnessoapp.dto.BlogRequest;
import com.decagon.fitnessoapp.service.serviceImplementation.BlogPostServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BlogPostControllerTest {
    @Mock
    BlogPostServiceImpl blogPostServiceImpl;
    @InjectMocks
    BlogPostController blogPostController;
    @Before
    public void setup() {
    }
    @Mock
    Authentication authentication;
    @Test
    public void test_updatePost(){
        BlogPostResponse blogPostUpdate = new BlogPostResponse();
        Long postId = 1L;
        String str = "Post updated successfully";
        when(blogPostServiceImpl.updatePost(blogPostUpdate, authentication)).thenReturn(new ResponseEntity<>(str, HttpStatus.OK));
        ResponseEntity<String> response = blogPostController.updatePost(blogPostUpdate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(str, response.getBody());
    }
    @Test
    public void test_deletePost(){
        Long id = 1L;
        String str = "Post deleted successfully";
        when(blogPostServiceImpl.deletePost(id)).thenReturn(new ResponseEntity<>(str, HttpStatus.OK));
        ResponseEntity<String> response = blogPostController.deletePost(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(str, response.getBody());
    }
   /* @Test
    public void test_addBlogPost(){
        BlogRequest blogRequest = new BlogRequest();
        String str = "Post saved successfully";
        when(blogPostServiceImpl.addBlogPost(blogRequest, authentication)).thenReturn(new ResponseEntity<>(str, HttpStatus.OK));
        ResponseEntity<String> response = blogPostController.createPost(blogRequest, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(str, response.getBody());
    }*/
    @Test
    public void test_GetAllPosts() {
        String sortBy = "id";
        int pageNo = 0;
        int pageSize = 10;
        List<BlogPostResponse> blogPostResponses = new ArrayList<>();
        when(blogPostServiceImpl.getAllPosts(pageNo, pageSize, sortBy)).thenReturn(blogPostResponses);
        ResponseEntity<List<BlogPostResponse>> response = blogPostController.getAllBlogPosts(pageNo, pageSize, sortBy);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blogPostResponses, response.getBody());
    }
}
