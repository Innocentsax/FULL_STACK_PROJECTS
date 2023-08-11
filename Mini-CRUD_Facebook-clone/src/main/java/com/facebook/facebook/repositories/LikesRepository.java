package com.facebook.facebook.repositories;

import com.facebook.facebook.entities.FacebookUser;
import com.facebook.facebook.entities.Likes;
import com.facebook.facebook.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByPostAndUser(Post post, FacebookUser currentUsername);
    Integer deleteByPostAndUser(Post post, FacebookUser currentUsername);
}
