package com.lesindmitrii.springlibrary.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity()
@Table()
@NamedEntityGraph(name = "loadBooks", attributeNodes = {@NamedAttributeNode(value = "books")})
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name")
    @NotEmpty(message = "Full name should be not empty")
    @Size(max = 256, message = "Full name length should less than 256 characters")
    private String fullName;

    @Column(name = "year_of_birth")
    @NotNull(message = "Year of birth should be not empty")
    @Min(value = 1900, message = "Year of birth should be greater 1900")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
