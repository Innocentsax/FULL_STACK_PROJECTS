package com.facebook.facebook.controller;
import com.facebook.facebook.entities.FacebookUser;
import com.facebook.facebook.entities.Post;
import com.facebook.facebook.services.PostServicesImpl;
import com.facebook.facebook.services.UserServicesImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    private final PostServicesImpl postService;


    @Autowired
    public PostController(PostServicesImpl postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        model.addAttribute("post", post.orElse(null));
        return "post";
    }

    @GetMapping("/posts/create")
    public String showCreatePostForm(Model model) {
        System.out.println("Bros do well oo");
        model.addAttribute("post", new Post());
        System.out.println("Omo this one mad");
        return "create-post";
    }

//    @GetMapping("posts/{id}/edit")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        Optional<Post> post = postService.getPostById(id);
//        model.addAttribute("post", post.orElse(null));
//        return "edit-post";
//    }
//    private Long getUserIdFromPrincipal(Principal principal) {
//        // Assuming the Principal object represents the username as a String
//        String username = principal.getName();
//
//        // Retrieve the user ID based on the username
//        Long userId = userServices.getUserIdByUsername(username);
//
//        return userId;
//   }




    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute("post") Post post) {
        post.setId(id);

        System.out.println("POST"+post);
       postService.updatePost(post);
        return "home";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        model.addAttribute("post", post.orElseThrow());
        return "edit-post";
    }


//    @PostMapping("/posts/{id}/delete")
//    public String deletePost(@PathVariable Long id) {
//        postService.deletePost(id);
//        return "redirect:/posts";
//    }
//    @PostMapping("/posts/{id}/delete")
//    public String deletePost(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
//        // Get the currently logged-in username
//        String loggedInUsername = principal.getName();
//
//        // Retrieve the post by its ID
//        Optional<Post> optionalPost = postService.getPostById(id);
//
//        // Check if the post exists and the logged-in user is the creator
//        if (optionalPost.isPresent() && optionalPost.get().getUser().getName().equals(loggedInUsername)) {
//            // Delete the post
//            postService.deletePost(id);
//        } else {
//            // Handle the case where the post doesn't exist or the user is not the creator
//            redirectAttributes.addFlashAttribute("error", "You are not allowed to delete this post.");
//        }
//
//        return "redirect:/posts";
//    }


    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Post> optionalPost = postService.getPostById(id);

        if (optionalPost.isPresent()) {
            postService.deletePost(id);
        } else {
            redirectAttributes.addFlashAttribute("error", "The post does not exist.");
        }

        return "redirect:/posts";
    }


    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute("post") Post post, @RequestParam("content") String content, HttpSession session) {
        // Get the authenticated user from the session
        System.out.println("oh mehn!");
        FacebookUser user = (FacebookUser) session.getAttribute("user");

        // Create a new Post object
//        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setTimestamp(LocalDateTime.now());

        // Save the post using the PostService
        postService.createPost(post);
        System.out.println("Chai!, there's God oo");

        return "redirect:/home";
    }







}
