package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.dao.PeopleDao;
import com.lesindmitrii.springlibrary.entity.Person;
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

    @Autowired
    public PeopleController(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
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
        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleDao.create(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String viewPerson(Model model, @PathVariable int id) {
        model.addAttribute("person", peopleDao.getById(id));
        return "people/view";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable int id) {
        model.addAttribute("person", peopleDao.getById(id));
        return "people/edit";
    }

    @DeleteMapping("/{id}")
    public String editPerson(@PathVariable int id) {
        peopleDao.deleteById(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String editPerson(@ModelAttribute("person") Person person, @PathVariable int id) {
        peopleDao.update(id, person);
        return "redirect:/people/" + person.getId();
    }
}
