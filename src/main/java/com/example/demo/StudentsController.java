package com.example.demo;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentsController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // http://localhost:8080/api/users
    //
    @GetMapping(
            value = "/students",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String getStudent(Model model) {
        String query = "SELECT id, name, groupSymbol FROM `students`";
        List<StudentEntity> students = this.jdbcTemplate.query(query, new StudentRowMapper());
        model.addAttribute("element", students.toString());
        return "showStudents";
    }

    @GetMapping(
            value = "/students/add",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String addStudent() {
        return "addStudent";
    }

    @PostMapping(
            path = "/students/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String createStudent(@RequestBody StudentEntity studentEntity) {
        String query = "INSERT INTO `students`(`name`, `groupSymbol`) VALUES ('"+studentEntity.getName()+"', '"+ studentEntity.getGroupSymbol()+"')";
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "addStudent";
    }

    @GetMapping(
            value = "/students/{id}/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String editStudent(Model model, @PathVariable String id) {
        String query = "SELECT `students`.id, name, groupSymbol FROM `students` WHERE `students`.id = " + id;
        List<StudentEntity> sclass = this.jdbcTemplate.query(query, new StudentRowMapper());
        model.addAttribute("element", sclass.get(0));
        return "editStudent";
    }

    @PostMapping(
            path = "/students/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String updateStudent(@RequestBody StudentEntity studentEntity) {
        String query = "UPDATE `students` SET `name`='"+studentEntity.getName()+"',`groupSymbol`='"+studentEntity.getGroupSymbol()+"' WHERE id=" + studentEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        return "/students";
    }

    @PostMapping(
            path = "/students/sendMail"
    )
    @ResponseBody
    public String sendMail(@RequestBody String text) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            URI address = new URI("http", null, "localhost", 6969, "/send", null, null);
            HttpPost httppost = new HttpPost(address);

// Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>(1);
            params.add(new BasicNameValuePair("text", text));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

//Execute and get the response.
            httpclient.execute(httppost);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "OK";
    }

    @PostMapping(
            path = "/students/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String deleteStudent(@RequestBody StudentEntity studentEntity) {
        String query = "DELETE FROM `students` WHERE id = " + studentEntity.getId();
        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }
        String query2 = "SELECT `students`.id, name, groupSymbol FROM `students`";
        List<StudentEntity> sclass = this.jdbcTemplate.query(query2, new StudentRowMapper());

        return sclass.toString();
    }

    private class StudentRowMapper implements RowMapper<StudentEntity> {
        @Override
        public StudentEntity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            StudentEntity student = new StudentEntity();
            student.setId(resultSet.getInt("id"));
            student.setName(resultSet.getString("name"));
            student.setGroupSymbol(resultSet.getString("groupSymbol"));
            return student;
        }
    }
}