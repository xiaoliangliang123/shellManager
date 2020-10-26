package com.shell.manager.data.model;

public class Server {

    private String id;
    private String groupName;
    private String name;
    private String ip;
    private String username;
    private String password;



    public Server(String id,String groupName,String name,String ip,String username,String password){
        this.id = id;
        this.groupName = groupName;
        this.name = name;
        this.ip = ip;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
