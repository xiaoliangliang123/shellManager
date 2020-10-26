package com.shell.manager.ui.listener;


import com.shell.manager.ui.listener.event.RefreshServerTreeEvent;
import com.shell.manager.ui.panel.LeftServerListPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RefreshServerTreeListener {


    @Autowired
    private LeftServerListPanel leftServerListPanel;

    @EventListener(classes={RefreshServerTreeEvent.class})
    public void listen(RefreshServerTreeEvent event) throws Exception {
        leftServerListPanel.reloadTree();
    }
}
