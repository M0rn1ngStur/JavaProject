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
        String query = "SELECT `attendance`.id, `students`.id as studentId, name, date, `classes`.id as classId, className, attendance  FROM `attendance`, `students`, `classes` WHERE `students`.id = studentId AND `classes`.id = classId";
        List<AttendanceEntity> attendance = this.jdbcTemplate.query(query, new AttendanceRowMapper());
        model.addAttribute("element", attendance.toString());
        return "showAttendance";
    }

    @GetMapping(
            value = "/attendance/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String addAttendance() {
        return "addAttendance";
    }

    @PostMapping(
            path = "/attendance/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createAttendance(@RequestBody AttendanceEntity attendanceEntity) {
        String query = "INSERT INTO `attendance`(`studentId`, `date`, `classId`, `attendance`) VALUES ('"+attendanceEntity.getStudentId()+"', '"+attendanceEntity.getDate()+"', '"+attendanceEntity.getClassId()+"', '"+(attendanceEntity.getAttendance() ? 1 : 0)+"')";
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "addAttendance";
    }

    @GetMapping(
            value = "/attendance/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String editAttendance(Model model, @PathVariable String id) {
        String query = "SELECT `students`.id as studentId, date, `classes`.id as classId, attendance FROM `attendance`, `students`, `classes` WHERE `students`.id = studentId AND `classes`.id = classId AND WHERE `attendance`.id = " + id;
        List<AttendanceEntity> sclass = this.jdbcTemplate.query(query, new AttendanceRowMapper());
        model.addAttribute("element", sclass.get(0));
        return "editAttendance";
    }

    @PostMapping(
            path = "/attendance/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String updateClass(@RequestBody AttendanceEntity attendanceEntity) {

        String query = "UPDATE `attendance` SET `studentId`='"+ attendanceEntity.getStudentId() +"',`date`='"+attendanceEntity.getDate()+"',`classId`='"+attendanceEntity.getClassId()+"',`attendance`='" + attendanceEntity.getAttendance() +"' WHERE id=" + attendanceEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "/attendance";
    }

    @PostMapping(
            path = "/attendance/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String deleteClass(@RequestBody AttendanceEntity attendanceEntity) {
        String query = "DELETE FROM `attendance` WHERE id = " + attendanceEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        String query2 = "SELECT `attendance`.id, `students`.id as studentId, name, date, `classes`.id as classId, className, attendance  FROM `attendance`, `students`, `classes` WHERE `students`.id = studentId AND `classes`.id = classId";
        List<AttendanceEntity> sclass = this.jdbcTemplate.query(query2, new AttendanceRowMapper());

        return sclass.toString();
    }

    private class AttendanceRowMapper implements RowMapper<AttendanceEntity> {
        @Override
        public AttendanceEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            AttendanceEntity attendance = new AttendanceEntity();
            attendance.setId(resultSet.getInt("id"));
            attendance.setStudentName(resultSet.getString("name"));
            attendance.setStudentId(resultSet.getInt("studentId"));
            attendance.setClassName(resultSet.getString("className"));
            attendance.setClassId(resultSet.getInt("classId"));
            attendance.setAttendance(resultSet.getBoolean("attendance"));
            attendance.setDate(resultSet.getString("date"));
            return attendance;
        }
    }
}