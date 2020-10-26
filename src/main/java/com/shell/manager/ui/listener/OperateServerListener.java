package com.shell.manager.ui.listener;

import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.data.model.Server;
import com.shell.manager.ui.listener.event.OperateServerEvent;
import com.shell.manager.ui.listener.event.RefreshServerTreeEvent;
import com.shell.manager.ui.panel.OperateContainerPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OperateServerListener {

    @Autowired
    private DatabaseUtil databaseUtil;

    @Autowired
    private OperateContainerPanel operateContainerPanel;

    @EventListener(classes = {OperateServerEvent.class})
    public void listen(OperateServerEvent event) throws Exception {
        String nodeName = (String) event.getSource();
        Server server = databaseUtil.getServerRespository().findByName(nodeName).get();
        operateContainerPanel.loadOperateServerWindow(server.getName());
        System.out.print(server.toString());
    }
}
