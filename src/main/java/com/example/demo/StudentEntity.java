package com.example.demo;

public class StudentEntity {

    private int id;
    private String name;
    private String groupSymbol;

    public StudentEntity() { }

    public StudentEntity(int id, String name, String groupSymbol) {
        this.id = id;
        this.name = name;
        this.groupSymbol = groupSymbol;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupSymbol() {
        return this.groupSymbol;
    }

    public void setGroupSymbol(String groupSymbol) {
        this.groupSymbol = groupSymbol;
    }

    @Override
    public String toString() {
        return "{ id: '" + String.valueOf(id) + "', name: '" + name + "', groupSymbol: '" + groupSymbol +"' }";
    }
}