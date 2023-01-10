package com.lesindmitrii.springlibrary.dao;

import com.lesindmitrii.springlibrary.entity.Book;
import com.lesindmitrii.springlibrary.mappers.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDao {
    private final JdbcTemplate jdbcTemplate;
    private final BookMapper bookMapper;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate, BookMapper bookMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookMapper = bookMapper;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select * from book", bookMapper);
    }

    public void create(Book book) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("insert into book (name, author, year_of_issue) values (?, ?, ?) returning book_id", book.getName(), book.getAuthor(), book.getYearOfIssue());
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
        jdbcTemplate.update("update book set name = ?, author = ?, year_of_issue = ?, person_id = ? where book_id = ?", book.getName(), book.getAuthor(), book.getYearOfIssue(), book.getPersonId(), id);
    }
}
