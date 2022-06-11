package com.example.demo;

import java.util.Date;

public class AttendanceEntity {

    private long id;
    private String className;
    private Date date;
    private String studentName;
    private boolean attendance;

    public AttendanceEntity() { }

    public AttendanceEntity(int id, String className, String studentName, Date date, boolean attendance ) {

        this.id = id;
        this.className = className;
        this.date = date;
        this.studentName = studentName;
        this.attendance = attendance;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getAttendance() {
        return this.attendance;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}