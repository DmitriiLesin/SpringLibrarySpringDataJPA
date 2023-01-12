package com.lesindmitrii.springlibrary.dao;

import com.lesindmitrii.springlibrary.entity.Book;
import com.lesindmitrii.springlibrary.entity.Person;
import com.lesindmitrii.springlibrary.mappers.BookMapper;
import com.lesindmitrii.springlibrary.mappers.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksDao {
    private final JdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;
    private final PeopleMapper peopleMapper;

    @Autowired
    public BooksDao(JdbcTemplate jdbcTemplate, BookMapper bookMapper, PeopleMapper peopleMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
        this.peopleMapper = peopleMapper;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select * from book", bookMapper);
    }

    public void create(Book book) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("insert into book (title, author, year_of_issue) values (?, ?, ?) returning book_id", book.getTitle(), book.getAuthor(), book.getYearOfIssue());
        rowSet.next();
        book.setId(rowSet.getInt("book_id"));
    }

    public Book getById(int id) {
        return jdbcTemplate.queryForObject("select * from book where book_id = ?", bookMapper, id);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("delete from book where book_id = ?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set title = ?, author = ?, year_of_issue = ? where book_id = ?", book.getTitle(), book.getAuthor(), book.getYearOfIssue(), id);
    }

    public List<Book> getPersonBooks(int personId) {
        return jdbcTemplate.query("select * from book where person_id = ?", bookMapper, personId);
    }

    public Optional<Person> getBookOwner(Integer id) {
        return jdbcTemplate.queryForStream("select person.* from person inner join book on person.person_id = book.person_id where book_id=?", peopleMapper, id).findAny();
    }

    public void assign(Integer bookId, Integer personId) {
        jdbcTemplate.update("update book set person_id = ? where book_id = ?", bookId, personId);
    }

    public void release(Integer bookId) {
        jdbcTemplate.update("update book set person_id = default where book_id = ?", bookId);
    }
}
