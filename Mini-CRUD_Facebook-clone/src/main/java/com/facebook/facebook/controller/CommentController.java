package com.facebook.facebook.controller;

import com.facebook.facebook.entities.Commentfac;
import com.facebook.facebook.entities.FacebookUser;
import com.facebook.facebook.repositories.CommentRepository;
import com.facebook.facebook.services.CommentServicesimpl;
import com.facebook.facebook.services.PostServicesImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/comments")
public class CommentController {
    private final CommentServicesimpl commentServices;
    private final PostServicesImpl postServices;
    private final CommentRepository commentRepository;
    private Long postId;
    private String commentText;
    private HttpSession session;


    public CommentController(CommentServicesimpl commentServices, PostServicesImpl postServices, CommentRepository commentRepository) {
        this.commentServices = commentServices;
        this.postServices = postServices;
        this.commentRepository = commentRepository;
    }

    @PostMapping("/comments/create")
    public String createComment(@RequestParam("postId") Long postId, @RequestParam("commentText") String commentText, HttpSession session) {
        this.postId = postId;
        this.commentText = commentText;
        this.session = session;
        // Get the authenticated user from the session
        FacebookUser user = (FacebookUser) session.getAttribute("user");

        // Create the comment using the CommentService
        commentServices.createComment(postId, commentText, user);

        return "redirect:/home";
    }


    @GetMapping("/comments/{postId}")
    public String getCommentsByPostId(@PathVariable Long postId, Model model) {
        List<Commentfac> comments = commentServices.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("comments/delete/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        // Find the comment by its ID
        Optional<Commentfac> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Commentfac comment = commentOptional.get();

            // Delete the comment from the database
            commentRepository.delete(comment);
        }

        // After deleting the comment, you can redirect the user back to the post or any desired page
        return "redirect:/home"; // Redirect to the posts page after deleting the comment
    }





//    @PostMapping("/comments/create")
//    public String createComment(@RequestParam("postId") Long postId, @RequestParam("commentText") String commentText, HttpSession session) {
//        // Get the authenticated user from the session
//        FacebookUser user = (FacebookUser) session.getAttribute("user");
//
//        // Create the comment using the CommentService
//        commentServices.createComment(postId, commentText, user);
//
//        return "redirect:/home";
//    }

}
