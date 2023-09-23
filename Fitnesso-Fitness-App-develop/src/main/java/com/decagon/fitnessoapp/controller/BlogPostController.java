package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.dto.BlogContext;
import com.decagon.fitnessoapp.dto.BlogPostResponse;
import com.decagon.fitnessoapp.service.BlogPostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
@CrossOrigin
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/post")
    public ResponseEntity<?> addBlogPost(@RequestBody BlogContext blogContext) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final BlogPostResponse blogPostResponse = blogPostService.addBlogPost(blogContext, authentication);
        return new ResponseEntity<>(blogPostResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/allposts")
    public ResponseEntity<List<BlogPostResponse>> getAllBlogPosts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<BlogPostResponse> blogPostResponses = blogPostService.getAllPosts(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(
                blogPostResponses, new HttpHeaders(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/updatepost/{postId}")
    public ResponseEntity<String> updatePost(@RequestBody BlogPostResponse blogPostUpdate){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return blogPostService.updatePost(blogPostUpdate, authentication);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/deletepost/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        return blogPostService.deletePost(postId);
    }

    @GetMapping("/blogposts/{number}")
    public ResponseEntity<?> getAllPosts(@PathVariable (name = "number") int pageNumber) {
        final Page<BlogPostResponse> blogPosts = blogPostService.getAllBlogPosts(pageNumber);
        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }

    @GetMapping("/blogposts/single_article/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        BlogPostResponse blogPostResponse = blogPostService.getPostById(postId);
        return new ResponseEntity<>(blogPostResponse, HttpStatus.OK);
    }
}
