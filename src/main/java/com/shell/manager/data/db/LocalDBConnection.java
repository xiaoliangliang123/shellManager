package com.shell.manager.data.db;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocalDBConnection {

    private static String Drivde = "org.sqlite.JDBC";

    public Connection getConnection() throws Exception {
        Class.forName(Drivde);
        String file = getClass().getResource("/db/designer.db").getPath();
        System.out.println("db:"+file);
        ClassPathResource classPathResource = new ClassPathResource("/db/designer.db");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:"+file);
        return connection;
    }








}
