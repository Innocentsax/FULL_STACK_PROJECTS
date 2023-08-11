package com.facebook.facebook.services.servicesInterfaces;

import com.facebook.facebook.entities.Commentfac;
import com.facebook.facebook.entities.FacebookUser;

import java.util.List;

public interface ICommentServices {

    Commentfac createComment(Long postId, String commentText, FacebookUser user);
    List<Commentfac> getCommentsByPostId(Long postId);
}
