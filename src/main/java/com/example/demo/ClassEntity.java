package com.example.demo;

import java.util.Date;

public class ClassEntity {

    private long id;
    private String className;

    public ClassEntity() { }

    public ClassEntity(int id, String className) {

        this.id = id;
        this.className = className;
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
}