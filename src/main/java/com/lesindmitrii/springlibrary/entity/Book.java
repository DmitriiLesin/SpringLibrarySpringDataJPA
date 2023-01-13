package com.lesindmitrii.springlibrary.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Book {
    private Integer id;

    @NotEmpty(message = "Name should be not empty")
    @Size(max = 256, message = "Name length should less than 256 characters")
    private String title;

    @NotEmpty(message = "Author should be not empty")
    @Size(max = 256, message = "Author length should less than 256 characters")
    private String author;


    @NotNull(message = "Year of issue should be not empty")
    @Min(value = 1900, message = "Year of issue should be greater 1900")
    private Integer yearOfIssue;

    private Integer personId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(Integer yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}
