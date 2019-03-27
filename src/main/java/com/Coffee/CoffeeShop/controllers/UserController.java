package com.Coffee.CoffeeShop.controllers;

import com.Coffee.CoffeeShop.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.Coffee.CoffeeShop.repositories.UserRepository;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/showAll";
    }

    @GetMapping("/users/add")
    public String showSignUpForm(User user) {
        return "users/add";
    }

    @PostMapping("/users")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "users/add";
        }

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid users id: " + id));

        model.addAttribute("user", user);
        return "users/update";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result) {
        if (result.hasErrors()) {
            user.setId(id);
            return "redirect:/users/" + id;
        }

        user.setId(id);
        userRepository.save(user);
        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid users Id:" + id));

        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "users/showAll";
    }

}
