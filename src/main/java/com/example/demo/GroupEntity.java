package com.example.demo;

import java.util.Date;

public class GroupEntity {

    private int id;
    private String groupSymbol;

    public GroupEntity() { }

    public GroupEntity(int id, String groupSymbol) {

        this.id = id;
        this.groupSymbol = groupSymbol;
    }

    public long getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupSymbol() {
        return this.groupSymbol;
    }

    public void setGroupSymbol(String groupSymbol) {
        this.groupSymbol = groupSymbol;
    }
}