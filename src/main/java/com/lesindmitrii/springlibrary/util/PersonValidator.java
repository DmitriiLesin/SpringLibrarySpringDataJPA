package com.lesindmitrii.springlibrary.util;

import com.lesindmitrii.springlibrary.model.Person;
import com.lesindmitrii.springlibrary.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Person person = (Person) target;
        if (!peopleService.fullNameExist(person.getFullName(), person.getId())) {
            return;
        }
        errors.rejectValue("fullName", "personWithFullNameExist", "Person with full name exists");
    }
}
