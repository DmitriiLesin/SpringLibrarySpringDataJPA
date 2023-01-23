package com.lesindmitrii.springlibrary.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.ZonedDateTime;

@Entity()
public class Book {

    public static final int DAYS_BEFORE_EXPIRE = 10;
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    @NotEmpty(message = "Name should be not empty")
    @Size(max = 256, message = "Name length should less than 256 characters")
    private String title;

    @Column()
    @NotEmpty(message = "Author should be not empty")
    @Size(max = 256, message = "Author length should less than 256 characters")
    private String author;


    @Column(name = "year_of_issue")
    @NotNull(message = "Year of issue should be not empty")
    @Min(value = 1900, message = "Year of issue should be greater 1900")
    private Integer yearOfIssue;

    @JoinColumn(name = "person_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;

    @Column(name = "assign_date")
    private ZonedDateTime assignDate;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ZonedDateTime getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(ZonedDateTime assignDate) {
        this.assignDate = assignDate;
    }

    public boolean isExpired() {

        if (assignDate == null) {
            return false;
        }

        return Duration.between(assignDate, ZonedDateTime.now()).toDays() > DAYS_BEFORE_EXPIRE;

    }
}
