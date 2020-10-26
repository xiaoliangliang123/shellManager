package com.shell.manager.data.model;

public class Group {

    private String id;
    private String name;


    public Group(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
