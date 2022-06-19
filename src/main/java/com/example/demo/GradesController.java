package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class GradesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // http://localhost:8080/api/users
    //
    @GetMapping(
            value = "/grades",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getGrades(Model model) {
        String query = "SELECT `grades`.id, className, name, grade FROM `grades`,`classes`, `students` WHERE `grades`.classId = `classes`.id AND `grades`.studentId = `students`.id";
        List<GradeEntity> grades = this.jdbcTemplate.query(query, new GradesRowMapper());
        model.addAttribute("element", grades.toString());
        return "showGrades";
    }

    private class GradesRowMapper implements RowMapper<GradeEntity> {
        @Override
        public GradeEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            GradeEntity grades = new GradeEntity();
            grades.setId(resultSet.getInt("id"));
            grades.setClassName(resultSet.getString("className"));
            grades.setGrade(resultSet.getInt("grade"));
            grades.setStudentName(resultSet.getString("studentName"));
            return grades;
        }
    }
}