package com.decagon.fitnessoapp.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.decagon.fitnessoapp.service.BlogPostService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BlogPostController.class})
@ExtendWith(SpringExtension.class)
class BlogPostControllerTest {
    @Autowired
    private BlogPostController blogPostController;

    @MockBean
    private BlogPostService blogPostService;



    @Test
    void testGetBlogPosts() throws Exception {
        when(this.blogPostService.getAllBlogPosts(anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/articles/blogposts/{number}", 10);
        MockMvcBuilders.standaloneSetup(this.blogPostController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }
}



