package com.lesindmitrii.springlibrary.repositories;

import com.lesindmitrii.springlibrary.model.Person;
import jakarta.persistence.NamedAttributeNode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    @EntityGraph(attributePaths = {"books"})
    Optional<Person> findWithBooksById(Integer id);

    boolean existsByFullName(String fullName);

    boolean existsByFullNameAndIdNot(String fullName, Integer id);
}
