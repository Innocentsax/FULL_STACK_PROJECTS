package com.decagon.fitnessoapp.repository;

import com.decagon.fitnessoapp.model.blog.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    Optional<BlogPost> findBlogPostsById(Long id);

    void deleteById(Long id);

}
