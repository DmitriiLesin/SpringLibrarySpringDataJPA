package com.lesindmitrii.springlibrary.dao;

import com.lesindmitrii.springlibrary.entity.Person;
import com.lesindmitrii.springlibrary.mappers.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import javax.sql.RowSet;
import java.util.List;

@Service
public class PeopleDao {
    private final JdbcTemplate jdbcTemplate;
    private final PeopleMapper peopleMapper;

    @Autowired
    public PeopleDao(JdbcTemplate jdbcTemplate, PeopleMapper peopleMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.peopleMapper = peopleMapper;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("select * from person", peopleMapper);
    }

    public void create(Person person) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("insert into person (full_name, year_of_birth) values (?, ?) returning person_id", person.getFullName(), person.getYearOfBirth());
        rowSet.next();
        person.setId(rowSet.getInt("person_id"));
    }

}
