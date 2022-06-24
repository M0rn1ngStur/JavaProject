package com.example.demo;

public class GradeEntity {

    private int id;
    private int classId;
    private int studentId;
    private String className;
    private int grade;
    private String studentName;

    public GradeEntity() { }

    public GradeEntity(int id, int classId, int studentId, String className, int grade, String studentName) {
        this.id = id;
        this.classId = classId;
        this.studentId = studentId;
        this.className = className;
        this.grade = grade;
        this.studentName = studentName;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getClassId() {
        return this.classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGrade() {
        return this.grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "{ id: '" + String.valueOf(id) + "', className: '" + className + "', grade: '" + String.valueOf(grade) + "', studentName: '" + studentName + "' }";
    }
}