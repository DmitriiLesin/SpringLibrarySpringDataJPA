package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.model.Person;
import com.lesindmitrii.springlibrary.services.PeopleService;
import com.lesindmitrii.springlibrary.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String list(Model model, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "person_per_page", required = false) Integer personPerPage, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        List<Person> people;
        if (page == null || personPerPage == null) {
            people = peopleService.getAll(sortByYear);
        } else {
            people = peopleService.getAll(page, personPerPage, sortByYear);
        }

        model.addAttribute("people", people);
        return "people/list";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.create(person);
        return "redirect:/people/" + person.getId();
    }

    @GetMapping("/{id}")
    public String viewPerson(Model model, @PathVariable Integer id) {
        model.addAttribute("person", peopleService.getByIdWithBooks(id));
        return "people/view";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable Integer id) {
        model.addAttribute("person", peopleService.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String patchPerson(@PathVariable Integer id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Integer id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }

}
