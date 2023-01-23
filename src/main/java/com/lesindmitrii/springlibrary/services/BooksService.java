package com.lesindmitrii.springlibrary.services;

import com.lesindmitrii.springlibrary.model.Book;
import com.lesindmitrii.springlibrary.model.Person;
import com.lesindmitrii.springlibrary.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> getAll(boolean sortByYear) {
        List<Book> books;
        if (sortByYear) {
            books = booksRepository.findAll(Sort.by("yearOfIssue"));
        } else {
            books = booksRepository.findAll();
        }
        return books;
    }

    public List<Book> getAll(Integer page, Integer personPerPage, boolean sortByYear) {
        List<Book> books;

        if (sortByYear) {
            books = booksRepository.findAll(PageRequest.of(page, personPerPage, Sort.by("yearOfIssue"))).getContent();
        } else {
            books = booksRepository.findAll(PageRequest.of(page, personPerPage)).getContent();
        }
        return books;
    }


    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }

    public Book getById(Integer id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Optional<Person> getBookOwner(Integer id) {
        Person person = getById(id).getPerson();
        if (person != null) {
            Hibernate.initialize(person);
        }
        return Optional.ofNullable(person);
    }

    @Transactional
    public void deleteById(Integer id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void update(Integer id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void release(Integer bookId) {
        Book book = getById(bookId);
        book.setPerson(null);
        book.setAssignDate(null);
    }

    @Transactional
    public void assign(Integer bookId, Integer personId) {
        Book book = getById(bookId);
        Person person = peopleService.getById(personId);
        book.setPerson(person);
        book.setAssignDate(ZonedDateTime.now());
    }

    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleStartingWith(query);
    }
}
