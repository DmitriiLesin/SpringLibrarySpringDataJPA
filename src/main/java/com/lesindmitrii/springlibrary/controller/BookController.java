package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.dao.BookDao;
import com.lesindmitrii.springlibrary.entity.Book;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;

    @Autowired
    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookDao.getAll());
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
        bookDao.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String viewBook(Model model, @PathVariable Integer id) {
        model.addAttribute("book", bookDao.getById(id));
        return "books/view";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable Integer id) {
        model.addAttribute("book", bookDao.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String patchBook(@ModelAttribute("book") Book book, @PathVariable Integer id) {
        bookDao.update(id, book);
        return "redirect:/books/" + book.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookDao.deleteById(id);
        return "redirect:/books";
    }

}
