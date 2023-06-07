package com.mpesocial.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.mpesocial.api.model.MpeUser;
import com.mpesocial.api.model.Comment;
import com.mpesocial.api.model.Post;
import com.mpesocial.api.service.MpeUserService;
import com.mpesocial.api.service.CommentService;
import com.mpesocial.api.service.PostService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("comment")
public class CommentController {
    private final PostService postService;
    private final MpeUserService mpeUserService;
    private final CommentService commentService;

    @Autowired
    public CommentController(PostService postService, MpeUserService mpeUserService, CommentService commentService) {
        this.postService = postService;
        this.mpeUserService = mpeUserService;
        this.commentService = commentService;
    }

    @Secured("ROLE_USER")
    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "Visitante";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<MpeUser> optionalMpeUser = this.mpeUserService.findByUsername(authUsername);
        Optional<Post> postOptional = this.postService.getById(id);
        if (postOptional.isPresent() && optionalMpeUser.isPresent()) {
            Comment comment = new Comment();
            comment.setPost(postOptional.get());
            comment.setUser(optionalMpeUser.get());
            model.addAttribute("comment", comment);
            
            System.err.println("GET comment/{id}: " + comment + "/" + id);
            
            return "commentForm";
        } else {
            System.err.println("Não foi possível encontrar uma publicação com ID: " + id + " ou o usuário: " + authUsername);
            
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/comment")
    public String validateComment(@Valid @ModelAttribute Comment comment, BindingResult bindingResult, SessionStatus sessionStatus) {
        System.err.println("POST comment: " + comment);
        if (bindingResult.hasErrors()) {
            System.err.println("O comentário não é válido!");
            return "commentForm";
        } else {
            this.commentService.save(comment);
            sessionStatus.setComplete();
            return "redirect:/post/" + comment.getPost().getId();
        }
    }

}
