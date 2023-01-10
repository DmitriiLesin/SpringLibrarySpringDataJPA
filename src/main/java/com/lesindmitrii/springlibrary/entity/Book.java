package com.lesindmitrii.springlibrary.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty(message = "Name should be not empty")
    @Size(max = 256, message = "Name length should less than 256 characters")
    private String name;

    @NotEmpty(message = "Author should be not empty")
    @Size(max = 256, message = "Author length should less than 256 characters")
    private String author;

    @Min(value = 1, message = "Year of issue should be not empty")
    private int yearOfIssue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }


}
