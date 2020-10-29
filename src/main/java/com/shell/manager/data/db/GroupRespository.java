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
public class GroupRespository implements DatabaseInterface<Group> {

    @Autowired
    private LocalDBConnection localDBConnection;

    public Group saveOrUpdate(Group group) throws Exception {

        if (findByName(group.getName()).isPresent()) {
            Connection connection = localDBConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "update groups set id = '" + group.getId() + "',  name = '" + group.getName() + "')";
            statement.execute(sql);
        } else {
            Connection connection = localDBConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "insert into groups('id','name')values('" + group.getId() + "','" + group.getName() + "')";
            statement.execute(sql);
        }
        return group;
    }

    @PostConstruct
    public void initGroup() throws Exception {
        Connection connection = localDBConnection.getConnection();
        Statement statement = connection.createStatement();
        StringBuilder sql = new StringBuilder("");
        sql.append("CREATE TABLE IF NOT EXISTS groups (");
        sql.append("id   CHAR(32),");
        sql.append("name CHAR(100)");
        sql.append(")");
        statement.execute(sql.toString());
    }


    public Optional<Group> findById(String id) throws Exception {
        Connection connection = localDBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from groups where id='" + id + "'";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            String name = rs.getString("name");
            Group group = new Group(id, name);
            return Optional.of(group);
        }
        return Optional.empty();
    }

    @Override
    public void deleteByName(String name) throws Exception {

    }

    @Override
    public List<Group> queryAll() throws Exception {

        synchronized (GroupRespository.class) {
            List<Group> groups = new ArrayList<>();
            Connection connection = localDBConnection.getConnection();
            Statement statement = connection.createStatement();
            String sql = "select * from groups";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                Group group = new Group(id, name);
                groups.add(group);
            }
            return groups;
        }
    }

    @Override
    public Optional<Group> findByName(String name) throws Exception {
        Connection connection = localDBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "select * from groups where name='" + name + "'";
        ResultSet rs = statement.executeQuery(sql);
        if (rs.next()) {
            String id = rs.getString("id");
            Group group = new Group(id, name);
            return Optional.of(group);
        }
        return Optional.empty();
    }

    @Override
    public List<Server> queryAllByParentName(String s) {
        return null;
    }


}
