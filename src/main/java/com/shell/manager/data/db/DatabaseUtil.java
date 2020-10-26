package com.shell.manager.data.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {


    @Autowired
    private GroupRespository groupRespository;

    @Autowired
    private ServerRespository serverRespository;


    public GroupRespository getGroupRespository() {
        return groupRespository;
    }

    public ServerRespository getServerRespository() {
        return serverRespository;   }
}
