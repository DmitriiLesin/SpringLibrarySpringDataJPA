package com.lesindmitrii.springlibrary.services;

import com.lesindmitrii.springlibrary.model.Person;
import com.lesindmitrii.springlibrary.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> getAll(boolean sortByYear) {
        List<Person> people;
        if (sortByYear) {
            people = peopleRepository.findAll(Sort.by("yearOfBirth"));
        } else {
            people = peopleRepository.findAll();
        }
        return people;
    }

    public List<Person> getAll(Integer page, Integer personPerPage, boolean sortByYear) {
        List<Person> people;

        if (sortByYear) {
            people = peopleRepository.findAll(PageRequest.of(page, personPerPage, Sort.by("yearOfBirth"))).getContent();
        } else {
            people = peopleRepository.findAll(PageRequest.of(page, personPerPage)).getContent();
        }
        return people;
    }

    @Transactional()
    public void create(Person person) {
        peopleRepository.save(person);
    }

    public Person getById(Integer id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public Person getByIdWithBooks(Integer id) {
        return peopleRepository.findWithBooksById(id).orElse(null);
    }

    @Transactional()
    public void deleteById(Integer id) {
        peopleRepository.deleteById(id);
    }

    @Transactional()
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    public boolean fullNameExist(String fullName, Integer id) {
        boolean fullNameExist;
        if (id == null) {
            fullNameExist = peopleRepository.existsByFullName(fullName);
        } else {
            fullNameExist = peopleRepository.existsByFullNameAndIdNot(fullName, id);
        }
        return fullNameExist;
    }

}
