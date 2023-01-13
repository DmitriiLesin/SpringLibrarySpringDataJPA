package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.dao.BooksDao;
import com.lesindmitrii.springlibrary.dao.PeopleDao;
import com.lesindmitrii.springlibrary.entity.Book;
import com.lesindmitrii.springlibrary.entity.Person;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksDao booksDao;
    private final PeopleDao peopleDao;

    @Autowired
    public BookController(BooksDao booksDao, PeopleDao peopleDao) {
        this.booksDao = booksDao;
        this.peopleDao = peopleDao;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", booksDao.getAll());
        return "books/list";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksDao.create(book);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/{id}")
    public String viewBook(Model model, @PathVariable Integer id, @ModelAttribute("person") Person person) {

        Optional<Person> owner = booksDao.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleDao.getAll());
        }
        model.addAttribute("book", booksDao.getById(id));
        return "books/view";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable Integer id, @ModelAttribute("person") Person person) {

        booksDao.assign(id, person.getId());

        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable Integer id) {

        booksDao.release(id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable Integer id) {
        model.addAttribute("book", booksDao.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String patchBook(@PathVariable Integer id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksDao.update(id, book);
        return "redirect:/books/" + book.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Integer id) {
        booksDao.deleteById(id);
        return "redirect:/books";
    }

}
