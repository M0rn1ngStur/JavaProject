package com.example.demo;

import java.util.Date;

public class ClassEntity {

    private String id;
    private String className;

    public ClassEntity() { }

    public ClassEntity(String id, String className) {

        this.id = id;
        this.className = className;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "{ id: '" + String.valueOf(id) + "', className: '" + className + "' }";
    }
}