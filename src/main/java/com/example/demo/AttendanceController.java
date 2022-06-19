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
public class AttendanceController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // http://localhost:8080/api/users
    //
    @GetMapping(
            value = "/attendance",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getAttendance(Model model) {
        String query = "SELECT `attendance`.id, name, date, className, attendance  FROM `attendance`, `students`, `classes` WHERE `students`.id = studentId AND `classes`.id = classId";
        List<AttendanceEntity> attendance = this.jdbcTemplate.query(query, new AttendanceRowMapper());
        model.addAttribute("element", attendance.toString());
        return "showAttendance";
    }

    private class AttendanceRowMapper implements RowMapper<AttendanceEntity> {
        @Override
        public AttendanceEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setId(resultSet.getInt("id"));
            attendance.setStudentName(resultSet.getString("studentName"));
            attendance.setClassName(resultSet.getString("className"));
            attendance.setAttendance(resultSet.getBoolean("attendance"));
            attendance.setDate(resultSet.getDate("date"));
            return attendance;
        }
    }
}