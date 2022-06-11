package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String greeting(Model model) {
        String query = "SELECT id, className  FROM `classes`";
        List<ClassEntity> sclass = this.jdbcTemplate.query(query, new AttendanceRowMapper());
        System.out.print(sclass.get(0));
        model.addAttribute("element", sclass);
        return "showClasses";
    }

    private class AttendanceRowMapper implements RowMapper<ClassEntity> {
        @Override
        public ClassEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            ClassEntity sclass = new ClassEntity();
            sclass.setId(resultSet.getLong("id"));
            sclass.setClassName(resultSet.getString("className"));
            return sclass;
        }
    }
}