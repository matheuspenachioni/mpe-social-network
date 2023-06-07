package com.mpesocial.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mpesocial.api.model.MpeUser;
import com.mpesocial.api.service.MpeUserService;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;

@Controller
@SessionAttributes("mpeUser")
public class SignupController {

    private final MpeUserService mpeUserService;

    @Autowired
    public SignupController(MpeUserService mpeUserService) {
        this.mpeUserService = mpeUserService;
    }

    @GetMapping("/signup")
    public String getRegisterForm(Model model) {
        MpeUser mpeUser = new MpeUser();
        model.addAttribute("mpeUser", mpeUser);
        
        return "registerForm";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute MpeUser mpeUser, BindingResult bindingResult, SessionStatus sessionStatus) throws RoleNotFoundException {
        System.err.println("newUser: " + mpeUser); 

        if (mpeUserService.findByUsername(mpeUser.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.username","Esse usuário já foi registrado!");
            System.err.println("Esse usuário já foi registrado!");
        }

        if (bindingResult.hasErrors()) {
            System.err.println("O usuário não é válido!");
            
            return "registerForm";
        }

        this.mpeUserService.saveNewMpeUser(mpeUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(mpeUser, mpeUser.getPassword(), mpeUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.err.println("SecurityContext Principal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        sessionStatus.setComplete();
        
        return "redirect:/";
    }
}
