package com.shell.manager.ui.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class OperateContainerPanel extends JTabbedPane{


    @PostConstruct
    public void init() {
        //super(JTabbedPane.CENTER);
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),"operate window"));
    }

    public void addOperateServerWindow(String name) throws Exception {

        OperateServerPanel operateServerPanel = new OperateServerPanel();
        operateServerPanel.setTitle(name);
        operateServerPanel.connectSSH(name);
        addTab( name ,operateServerPanel)  ;
        this.updateUI();
    }

}
