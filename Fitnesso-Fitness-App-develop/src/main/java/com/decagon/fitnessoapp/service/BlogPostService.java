package com.decagon.fitnessoapp.service;

import com.decagon.fitnessoapp.dto.BlogContext;
import com.decagon.fitnessoapp.dto.BlogPostResponse;
import com.decagon.fitnessoapp.model.blog.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BlogPostService {

    ResponseEntity<String> deletePost(Long id);

    List<BlogPostResponse> getAllPosts(Integer pageNo, Integer pageSize, String sortBy);

    Page<BlogPostResponse> getAllBlogPosts(int pageNo);

    ResponseEntity<String> updatePost(BlogPostResponse blogPostUpdated, Authentication authentication);

    BlogPostResponse addBlogPost(BlogContext blogContext, Authentication authentication);

    BlogPostResponse getPostById(Long id);
}
