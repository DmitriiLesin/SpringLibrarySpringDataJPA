package com.lesindmitrii.springlibrary.repositories;

import com.lesindmitrii.springlibrary.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    @EntityGraph(attributePaths = {"person"})
    List<Book> findByTitleStartingWith(String query);
}
