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
public class ClassesController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // http://localhost:8080/api/users
    //
    @GetMapping(
            value = "/classes",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getClasses(Model model) {
        String query = "SELECT id, className  FROM `classes`";
        List<ClassEntity> sclass = this.jdbcTemplate.query(query, new ClassesRowMapper());
        model.addAttribute("element", sclass.toString());
        return "showClasses";
    }

    @GetMapping(
            value = "/classes/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String addClass() {
        return "addClass";
    }

    @PostMapping(
            path = "/classes/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createClass(@RequestBody ClassEntity classEntity) {
        String query = "INSERT INTO `classes`(`className`) VALUES ('" + classEntity.getClassName() + "')";
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "addClass";
    }

    @GetMapping(
            value = "/classes/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String editClass(Model model, @PathVariable String id) {
        String query = "SELECT id, className  FROM `classes` WHERE id = " + id;
        List<ClassEntity> sclass = this.jdbcTemplate.query(query, new ClassesRowMapper());
        model.addAttribute("element", sclass.get(0));
        return "editClass";
    }

    @PostMapping(
            path = "/classes/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String updateClass(@RequestBody ClassEntity classEntity) {
        String query = "UPDATE `classes` SET `className`='" + classEntity.getClassName() + "' WHERE id=" + classEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "/classes";
    }

    @PostMapping(
            path = "/classes/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String deleteClass(@RequestBody ClassEntity classEntity) {
        String query = "DELETE FROM `classes` WHERE id = " + classEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        String query2 = "SELECT id, className  FROM `classes`";
        List<ClassEntity> sclass = this.jdbcTemplate.query(query2, new ClassesRowMapper());

        return sclass.toString();
    }

    private class ClassesRowMapper implements RowMapper<ClassEntity> {
        @Override
        public ClassEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            ClassEntity sclass = new ClassEntity();
            sclass.setId(resultSet.getString("id"));
            sclass.setClassName(resultSet.getString("className"));
            return sclass;
        }
    }
}