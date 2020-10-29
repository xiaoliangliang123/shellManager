package com.shell.manager.data.db;

import com.shell.manager.data.model.Group;
import com.shell.manager.data.model.Server;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DatabaseInterface<T> {

    public T saveOrUpdate(T group) throws Exception;


    public Optional<T> findById(String id) throws Exception;

    public void deleteByName(String name) throws Exception;


    List<T> queryAll() throws Exception;

    Optional<T> findByName(String content) throws Exception;

    List<Server> queryAllByParentName(String s) throws Exception;
}
