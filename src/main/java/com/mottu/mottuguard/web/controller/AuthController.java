package com.mottu.mottuguard.web.controller;

import com.mottu.mottuguard.services.UserService;
import com.mottu.mottuguard.web.dto.RegisterForm;
import jakarta.validation.Valid; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService users;
    public AuthController(UserService u){this.users=u;}

    @GetMapping("/login") String login(){ return "auth/login"; }

    @GetMapping("/register") String registerForm(Model m){ m.addAttribute("form", new RegisterForm()); return "auth/register"; }
    @PostMapping("/register") String register(@ModelAttribute("form") @Valid RegisterForm f, BindingResult br){
        if(br.hasErrors()) return "auth/register";
        users.register(f.getName(), f.getEmail(), f.getPassword(), false);
        return "redirect:/login?ok";
    }
}
