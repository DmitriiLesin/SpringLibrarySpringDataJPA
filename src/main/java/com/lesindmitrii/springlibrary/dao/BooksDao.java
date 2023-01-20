package com.lesindmitrii.springlibrary.dao;

import com.lesindmitrii.springlibrary.entity.Book;
import com.lesindmitrii.springlibrary.entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BooksDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BooksDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" select b from Book b", Book.class).getResultList();
    }

    @Transactional()
    public void create(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional(readOnly = true)
    public Book getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getBookOwner(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("select b.person  from Book b where b.id =:id", Person.class);
        query.setParameter("id", id);
        return query.uniqueResultOptional();
    }

    @Transactional()
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.getReference(Book.class, id);
        session.remove(book);
    }

    @Transactional()
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book updateBook = session.get(Book.class, id);
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setYearOfIssue(book.getYearOfIssue());
    }

    @Transactional()
    public void assign(Integer bookId, Integer personId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
        Person person = session.getReference(Person.class, personId);
        book.setPerson(person);
    }

    @Transactional()
    public void release(Integer bookId) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, bookId);
        book.setPerson(null);
    }
}
