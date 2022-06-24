package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        String query = "SELECT `grades`.id, `students`.id as studentId, `classes`.id as classId, className, name, grade FROM `grades`,`classes`, `students` WHERE `grades`.classId = `classes`.id AND `grades`.studentId = `students`.id";
        List<GradeEntity> grades = this.jdbcTemplate.query(query, new GradesRowMapper());
        model.addAttribute("element", grades.toString());
        return "showGrades";
    }

    @GetMapping(
            value = "/grades/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String addGrade() {
        return "addGrade";
    }

    @PostMapping(
            path = "/grades/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createGrade(@RequestBody GradeEntity gradeEntity) {
        String query = "INSERT INTO `grades`(`grade`, `classId`, `studentId`) VALUES ('" + gradeEntity.getGrade() + "', '"+ gradeEntity.getClassId()+"', '"+gradeEntity.getStudentId()+"')";
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println(e);
        }
        return "addGrade";
    }

    @GetMapping(
            value = "/grades/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String editGrade(Model model, @PathVariable String id) {
        String query = "SELECT `grades`.id, `students`.id as studentId, `classes`.id as classId, className, name, grade FROM `grades`,`classes`, `students` WHERE `grades`.classId = `classes`.id AND `grades`.studentId = `students`.id AND `grades`.id = " + id;
        List<GradeEntity> sclass = this.jdbcTemplate.query(query, new GradesRowMapper());
        model.addAttribute("element", sclass.get(0));
        return "editGrade";
    }

    @PostMapping(
            path = "/grades/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String updateGrade(@RequestBody GradeEntity gradeEntity) {
        String query = "UPDATE `grades` SET `grade`='" + gradeEntity.getGrade()+"',`classId`='"+gradeEntity.getClassId()+"',`studentId`='"+gradeEntity.getStudentId()+"' WHERE id=" + gradeEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "/grades";
    }

    @PostMapping(
            path = "/grades/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String deleteGrade(@RequestBody GradeEntity gradeEntity) {
        String query = "DELETE FROM `grades` WHERE id = " + gradeEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        String query2 = "SELECT `grades`.id, `students`.id as studentId, `classes`.id as classId, className, name, grade FROM `grades`,`classes`, `students` WHERE `grades`.classId = `classes`.id AND `grades`.studentId = `students`.id";
        List<GradeEntity> sclass = this.jdbcTemplate.query(query2, new GradesRowMapper());

        return sclass.toString();
    }

    private class GradesRowMapper implements RowMapper<GradeEntity> {
        @Override
        public GradeEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            GradeEntity grades = new GradeEntity();
            grades.setId(resultSet.getInt("id"));
            grades.setClassId(resultSet.getInt("classId"));
            grades.setStudentId(resultSet.getInt("studentId"));
            grades.setClassName(resultSet.getString("className"));
            grades.setGrade(resultSet.getInt("grade"));
            grades.setStudentName(resultSet.getString("name"));
            return grades;
        }
    }
}