package com.shell.manager.data.db;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
        Connection connection = DriverManager.getConnection("jdbc:sqlite:db/designer.db");
        return connection;
    }








}
