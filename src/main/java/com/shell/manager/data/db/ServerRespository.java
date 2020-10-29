package com.shell.manager.data.db;

import com.shell.manager.data.model.Group;
import com.shell.manager.data.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ServerRespository implements DatabaseInterface<Server> {

    @Autowired
    private LocalDBConnection localDBConnection;

    public Server saveOrUpdate(Server server) throws Exception {

        Connection connection = localDBConnection.getConnection();

        try {
            if(findByName(server.getName()).isPresent()){
                Statement statement = connection.createStatement();
                String sql = "update server set id = '" + server.getId() + "', groupName = '"+server.getGroupName()+"' ,  name = '" + server.getName() + "',ip =  '"+server.getIp()+"',username ='"+server.getUsername()+"',password='"+server.getPassword()+"')";
                statement.execute(sql);
            }else {
                Statement statement = connection.createStatement();
                String sql = "insert into server('id','groupName','name','ip','username','password')values('" + server.getId() + "', '"+server.getGroupName()+"','" + server.getName() + "','"+server.getIp()+"','"+server.getUsername()+"','"+server.getPassword()+"')";
                statement.execute(sql);
            }
            return server;
        }finally {
            connection.close();
        }

    }

    @PostConstruct
    public void initServer() throws Exception {
        Connection connection = localDBConnection.getConnection();
        Statement statement = connection.createStatement();
        StringBuilder sql = new StringBuilder("");
        sql.append("CREATE TABLE IF NOT EXISTS server (");
        sql.append("id      CHAR(32),");
        sql.append("groupName   CHAR(100),");
        sql.append("name CHAR(100),");
        sql.append("ip CHAR(32),");
        sql.append("username CHAR(100),");
        sql.append("password CHAR(100)");
        sql.append(")");
        statement.execute(sql.toString());
    }

    public Optional<Server> findById(String id) throws Exception {
        Connection connection = localDBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from groups where id='"+id+"'";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            String name = rs.getString("name");
            String groupName = rs.getString("groupName");
            String ip = rs.getString("ip");
            String username = rs.getString("username");
            String password = rs.getString("password");

            Server server = new Server(id,groupName,name,ip,username,password);
            return  Optional.of(server);
        }
        return  Optional.empty();
    }

    @Override
    public void deleteByName(String name) throws Exception {
        Connection connection = localDBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "delete from server where name ='" + name + "'";
            statement.executeUpdate(sql);
        }finally {
            connection.close();
        }


    }

    @Override
    public List<Server> queryAll() throws Exception {

            List<Server> servers = new ArrayList<>();
            Connection connection = localDBConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from groups";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String groupName = rs.getString("groupName");
                String ip = rs.getString("ip");
                String username = rs.getString("username");
                String password = rs.getString("password");

                Server server = new Server(id, groupName, name, ip, username, password);
                servers.add(server);
            }

            return servers;

    }

    @Override
    public Optional<Server> findByName(String name) throws Exception {


        Connection connection = localDBConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from server where name='"+name+"'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                String id = rs.getString("id");
                String groupName = rs.getString("groupName");
                String ip = rs.getString("ip");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Server server = new Server(id,groupName,name,ip,username,password);
                return  Optional.of(server);
            }
            return  Optional.empty();
        }finally {
            connection.close();
        }

    }

    @Override
    public List<Server> queryAllByParentName(String parentName) throws Exception {
        Connection connection = localDBConnection.getConnection();

        try {
            List<Server> servers = new ArrayList<>();

            Statement statement = connection.createStatement();
            String sql = "select * from server where groupName = '"+parentName+"'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String groupName = rs.getString("groupName");
                String ip = rs.getString("ip");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Server server = new Server(id,groupName,name,ip,username,password);
                servers.add(server);
            }
            return servers;
        }finally {
            connection.close();
        }

    }


}
