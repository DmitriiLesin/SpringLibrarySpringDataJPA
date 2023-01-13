package com.lesindmitrii.springlibrary.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Person {
    private Integer id;

    @NotEmpty(message = "Full name should be not empty")
    @Size(max = 256, message = "Full name length should less than 256 characters")
    private String fullName;

    @NotNull(message = "Year of birth should be not empty")
    @Min(value = 1900, message = "Year of birth should be greater 1900")
    private Integer yearOfBirth;

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
}
