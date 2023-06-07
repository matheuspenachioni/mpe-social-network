package com.mpesocial.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.mpesocial.api.model.MpeUser;
import com.mpesocial.api.model.Post;
import com.mpesocial.api.service.MpeUserService;
import com.mpesocial.api.service.PostService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@SessionAttributes("post")
public class PostController {

    private final PostService postService;
    private final MpeUserService mpeUserService;

    @Autowired
    public PostController(PostService postService, MpeUserService mpeUserService) {
        this.postService = postService;
        this.mpeUserService = mpeUserService;
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "Visitante";
        
        if (principal != null) {
            authUsername = principal.getName();
        }
        
        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);

            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("isOwner", true);
            }
            
            return "post";
        } else {
            return "404";
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/createNewPost")
    public String createNewPost(Model model, Principal principal) {
        String authUsername = "Visitante";
        
        if (principal != null) {
            authUsername = principal.getName();
        }
        
        Optional<MpeUser> optionalMpeUser = this.mpeUserService.findByUsername(authUsername);

        if (optionalMpeUser.isPresent()) {
            Post post = new Post();
            post.setUser(optionalMpeUser.get());
            model.addAttribute("post", post);
            
            return "postForm";
        } else {
            return "error";
        }
    }

    @Secured("ROLE_USER")
    @PostMapping("/createNewPost")
    public String createNewPost(@Valid @ModelAttribute Post post, BindingResult bindingResult, SessionStatus sessionStatus) {
        System.err.println("POST post: " + post);
        if (bindingResult.hasErrors()) {
            System.err.println("A publicação não é válida!");
            return "postForm";
        }
        
        this.postService.save(post);
        System.err.println("SAVE post: " + post);
        sessionStatus.setComplete();
        
        return "redirect:/post/" + post.getId();
    }

    @Secured("ROLE_USER")
    @GetMapping("editPost/{id}")
    public String editPost(@PathVariable Long id, Model model, Principal principal) {
        String authUsername = "Visitante";
        
        if (principal != null) {
            authUsername = principal.getName();
        }
        
        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (authUsername.equals(post.getUser().getUsername())) {
                model.addAttribute("post", post);
                System.err.println("EDIT post: " + post);
                
                return "postForm";
            } else {
                System.err.println("O usuário não tem permissão para alterar a publicação de ID: " + id);

                return "403";
            }
        } else {
            System.err.println("Não foi encontrada uma publicação com ID: " + id);

            return "error";
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable Long id, Principal principal) {
        String authUsername = "Visitante";
        
        if (principal != null) {
            authUsername = principal.getName();
        }
        
        Optional<Post> optionalPost = this.postService.getById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (authUsername.equals(post.getUser().getUsername())) {
                this.postService.delete(post);
                System.err.println("DELETED post: " + post);

                return "redirect:/";
            } else {
                System.err.println("O usuário não tem permissão para deletar a publicação de ID: " + id);
                return "403";
            }
        } else {
            System.err.println("Não foi encontrada uma publicação com ID: " + id);
            return "error";
        }
    }

}
