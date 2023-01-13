package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.dao.BooksDao;
import com.lesindmitrii.springlibrary.dao.PeopleDao;
import com.lesindmitrii.springlibrary.entity.Person;
import com.lesindmitrii.springlibrary.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDao peopleDao;
    private final BooksDao booksDao;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleDao peopleDao, BooksDao booksDao, PersonValidator personValidator) {
        this.peopleDao = peopleDao;
        this.booksDao = booksDao;
        this.personValidator = personValidator;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("people", peopleDao.getAll());
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
        peopleDao.create(person);
        return "redirect:/people/" + person.getId();
    }

    @GetMapping("/{id}")
    public String viewPerson(Model model, @PathVariable Integer id) {
        model.addAttribute("person", peopleDao.getById(id));
        model.addAttribute("books", booksDao.getPersonBooks(id));
        return "people/view";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable Integer id) {
        model.addAttribute("person", peopleDao.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String patchPerson(@PathVariable Integer id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleDao.update(id, person);
        return "redirect:/people/" + person.getId();
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable Integer id) {
        peopleDao.deleteById(id);
        return "redirect:/people";
    }

}
