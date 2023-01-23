package com.lesindmitrii.springlibrary.controller;

import com.lesindmitrii.springlibrary.model.Book;
import com.lesindmitrii.springlibrary.model.Person;
import com.lesindmitrii.springlibrary.services.BooksService;
import com.lesindmitrii.springlibrary.services.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String list(Model model , @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "books_per_page", required = false) Integer booksPerPage, @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        List<Book> books;
        if (page == null || booksPerPage == null) {
            books = booksService.getAll(sortByYear);
        } else {
            books = booksService.getAll(page, booksPerPage, sortByYear);
        }

        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.searchByTitle(query));
        return "books/search";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.create(book);
        return "redirect:/books/" + book.getId();
    }

    @GetMapping("/{id}")
    public String viewBook(Model model, @PathVariable Integer id, @ModelAttribute("person") Person person) {

        Optional<Person> owner = booksService.getBookOwner(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.getAll(false));
        }
        model.addAttribute("book", booksService.getById(id));
        return "books/view";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable Integer id, @ModelAttribute("person") Person person) {

        booksService.assign(id, person.getId());

        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/release")
    public String releaseBook(@PathVariable Integer id) {

        booksService.release(id);

        return "redirect:/books/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable Integer id) {
        model.addAttribute("book", booksService.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String patchBook(@PathVariable Integer id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books/" + book.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Integer id) {
        booksService.deleteById(id);
        return "redirect:/books";
    }

}
