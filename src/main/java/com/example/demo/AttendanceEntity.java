package com.example.demo;

public class AttendanceEntity {

    private int id;
    private String className;
    private int classId;
    private String date;
    private String studentName;
    private int studentId;
    private boolean attendance;

    public AttendanceEntity() { }

    public AttendanceEntity(int id, String className, int classId, String studentName, int studentId, String date, boolean attendance ) {
        this.id = id;
        this.className = className;
        this.classId = classId;
        this.date = date;
        this.studentName = studentName;
        this.studentId = studentId;
        this.attendance = attendance;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getClassId() {
        return this.classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
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

    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "{ id: '" + String.valueOf(id) + "', className: '" + className + "', classId: '" + String.valueOf(classId) + "', date: '" + date.toString() + "', studentName: '" + studentName + "', studentId: '" + String.valueOf(studentId) + "', attendance: '" + attendance + "' }";
    }
}