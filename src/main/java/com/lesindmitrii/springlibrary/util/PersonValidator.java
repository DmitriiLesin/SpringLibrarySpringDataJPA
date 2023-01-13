package com.lesindmitrii.springlibrary.util;

import com.lesindmitrii.springlibrary.dao.PeopleDao;
import com.lesindmitrii.springlibrary.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleDao peopleDao;

    @Autowired
    public PersonValidator(PeopleDao peopleDao) {
        this.peopleDao = peopleDao;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Person person = (Person) target;
        if (!peopleDao.fullNameExist(person.getFullName())) {
            return;
        }
        errors.rejectValue("fullName", "personWithFullNameExist", "Person with full name exists");
    }
}
