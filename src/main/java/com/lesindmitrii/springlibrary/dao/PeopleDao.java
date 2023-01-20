package com.lesindmitrii.springlibrary.dao;

import com.lesindmitrii.springlibrary.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public PeopleDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" select p from Person p", Person.class).getResultList();
    }

    @Transactional()
    public void create(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional(readOnly = true)
    public Person getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Person getByIdWithBooks(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("select p from Person p where p.id =:id", Person.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", session.getEntityGraph("loadBooks"));
        return query.getSingleResult();
    }

    @Transactional()
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.getReference(Person.class, id);
        session.remove(person);
    }

    @Transactional()
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person updatePerson = session.get(Person.class, id);
        updatePerson.setFullName(person.getFullName());
        updatePerson.setYearOfBirth(person.getYearOfBirth());
    }

    @Transactional(readOnly = true)
    public boolean fullNameExist(String fullName) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("select p from Person p where p.fullName =:fullName", Person.class);
        query.setParameter("fullName", fullName);
        return query.getSingleResultOrNull() != null;
    }
}
