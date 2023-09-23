package com.decagon.fitnessoapp.service.serviceImplementation;

import com.decagon.fitnessoapp.dto.BlogContext;
import com.decagon.fitnessoapp.dto.BlogPostResponse;
import com.decagon.fitnessoapp.exception.CustomServiceExceptions;
import com.decagon.fitnessoapp.model.blog.BlogPost;
import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.repository.BlogPostRepository;
import com.decagon.fitnessoapp.repository.PersonRepository;
import com.decagon.fitnessoapp.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<BlogPostResponse> getAllPosts(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable postPage = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<BlogPost> pagedPosts = blogPostRepository.findAll(postPage);
        List<BlogPostResponse> listOfBlogs = new ArrayList<>();
        if(pagedPosts.hasContent()){
            for (BlogPost blogPost : pagedPosts) {
                BlogPostResponse blog = modelMapper.map(blogPost, BlogPostResponse.class);
                listOfBlogs.add(blog);
            }
        }
        return listOfBlogs;

    }

    @Override
    public Page<BlogPostResponse> getAllBlogPosts(int pageNo) {
        int pageSize = 10;
        int skipCount = (pageNo - 1) * pageSize;

        List<BlogPost> blogList = blogPostRepository.findAll();

        final List<BlogPostResponse> blogPostDtoList = blogList.stream()
                .map(x -> modelMapper.map(x, BlogPostResponse.class))
                .collect(Collectors.toList())
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable blogPage = PageRequest.of(pageNo, pageSize, Sort.by("productName").ascending());

        return new PageImpl<>(blogPostDtoList, blogPage, blogList.size());
    }

    @Override
    public ResponseEntity<String> updatePost(BlogPostResponse blogPostUpdated, Authentication authentication){
        Person person = personRepository.findPersonByUserName(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Admin does not exist"));
        BlogPost blogPost = blogPostRepository.findBlogPostsById(blogPostUpdated.getId()).orElseThrow(
                ()-> new CustomServiceExceptions("Post Not Found"));
        //BlogPost upDatedBlog = modelMapper.map(blogPostUpdated, BlogPost.class);
        blogPost.setPerson(person);
        blogPost.setTitle(blogPostUpdated.getTitle());
        blogPost.setImage(blogPostUpdated.getImage());
        blogPost.setContent(blogPostUpdated.getContent());
        blogPost.setAuthorName(blogPostUpdated.getAuthorName());
        blogPost.setBiography(blogPostUpdated.getBiography());
        blogPost.setContact(blogPostUpdated.getContact());
        blogPostRepository.save(blogPost);
        return ResponseEntity.ok().body("Post updated successfully");


    }

    @Override
    @Transactional
    public ResponseEntity<String> deletePost(Long id) {
        BlogPost blogPost = blogPostRepository.findBlogPostsById(id).orElseThrow(
                ()-> new CustomServiceExceptions("Post Not Found"));
        //blogPostRepository.deleteById(id);
        System.out.println("Delete this?: " + blogPost);
        blogPostRepository.delete(blogPost);
        return ResponseEntity.ok().body("Post deleted successfully");

    }


    @Override
    public BlogPostResponse addBlogPost(BlogContext blogContext, Authentication authentication) {
        Person person = personRepository.findPersonByUserName(authentication.getName())
                .orElseThrow(()-> new UsernameNotFoundException("Admin does not exist"));
        BlogPost blogPost = modelMapper.map(blogContext, BlogPost.class);
        blogPost.setPerson(person);
        blogPostRepository.save(blogPost);
        return modelMapper.map(blogPost, BlogPostResponse.class);
    }

    @Override
    public BlogPostResponse getPostById(Long id) {
        System.out.println("Before Blog post");
        BlogPost blogPost = blogPostRepository.getById(id);
        System.out.println("After BlogPost" + blogPost);
        return modelMapper.map(blogPost, BlogPostResponse.class);
    }
}
