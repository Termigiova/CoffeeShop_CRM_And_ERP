package com.Coffee.CoffeeShop.controllers;

import com.Coffee.CoffeeShop.models.Collaborator;
import com.Coffee.CoffeeShop.repositories.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CollaboratorController {

    @Autowired
    CollaboratorRepository collaboratorRepository;

    @GetMapping("/collaborators")
    public String getAllCollaborators(Model model) {
        model.addAttribute("collaborators", collaboratorRepository.findAll());
        return "collaborators/showAll";
    }

    @GetMapping("/collaborators/add")
    public String showSignUpForm(Collaborator collaborator) {
        return "collaborators/add";
    }

    @PostMapping("/collaborators")
    public String addCollaborator(@Valid Collaborator collaborator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "collaborators/add";
        }

        collaboratorRepository.save(collaborator);
        return "redirect:/collaborators";
    }

    @GetMapping("/collaborators/{id}")
    public String getCollaborator(@PathVariable("id") long id, Model model) {
        Collaborator collaborator = collaboratorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid collaborators id: " + id));

        model.addAttribute("collaborator", collaborator);
        return "collaborators/update";
    }

    @PostMapping("/collaborators/{id}")
    public String updateCollaborator(@PathVariable("id") long id, @Valid Collaborator collaborator,
                             BindingResult result) {
        if (result.hasErrors()) {
            collaborator.setId(id);
            return "redirect:/collaborators/" + id;
        }

        collaborator.setId(id);
        collaboratorRepository.save(collaborator);
        return "redirect:/collaborators";
    }

    @DeleteMapping("/collaborators/{id}")
    public String deleteCollaborator(@PathVariable("id") long id, Model model) {
        Collaborator collaborator = collaboratorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid collaborators Id:" + id));

        collaboratorRepository.delete(collaborator);
        model.addAttribute("collaborators", collaboratorRepository.findAll());
        return "collaborators/showAll";
    }

}
