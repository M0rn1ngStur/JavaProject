package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class StudentsController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // http://localhost:8080/api/users
    //
    @GetMapping(
            value = "/api/students",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public List<StudentEntity> getStudent() {
        String query = "SELECT `students`.id, name, groupSymbol FROM `students`, `groups` WHERE `groups`.id = `students`.groupId";
        List<StudentEntity> students = this.jdbcTemplate.query(query, new StudentRowMapper());
        return students;
    }

    private class StudentRowMapper implements RowMapper<StudentEntity> {
        @Override
        public StudentEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            StudentEntity student = new StudentEntity();
            student.setId(resultSet.getLong("id"));
            student.setName(resultSet.getString("name"));
            student.setGroupSymbol(resultSet.getString("groupSymbol"));
            return student;
        }
    }
}