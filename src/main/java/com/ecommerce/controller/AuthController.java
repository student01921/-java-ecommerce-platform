package com.ecommerce.controller;

import com.ecommerce.dto.UserRegistrationDto;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                               BindingResult result,
                               Model model) {

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
        }
        if (userService.emailExists(dto.getEmail())) {
            result.rejectValue("email", "error.user", "An account with this email already exists");
        }
        // Only BUYER and SELLER may self-register
        if (!"BUYER".equals(dto.getRole()) && !"SELLER".equals(dto.getRole())) {
            result.rejectValue("role", "error.user", "Invalid role selected");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(dto);
        return "redirect:/login?registered";
    }
}
