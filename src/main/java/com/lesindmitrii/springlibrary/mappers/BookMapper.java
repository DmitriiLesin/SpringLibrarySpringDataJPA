package com.lesindmitrii.springlibrary.mappers;

import com.lesindmitrii.springlibrary.entity.Book;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setYearOfIssue(rs.getInt("year_of_issue"));
        book.setPersonId(rs.getInt("person_id"));
        return book;
    }
}
