package com.lesindmitrii.springlibrary.mappers;

import com.lesindmitrii.springlibrary.entity.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PeopleMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("person_id"));
        person.setFullName(rs.getString("full_name"));
        person.setYearOfBirth(rs.getInt("year_of_birth"));
        return person;
    }
}
