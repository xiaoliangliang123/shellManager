package com.shell.manager.ui.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class OperateContainerPanel extends JPanel {

    @Autowired
    private OperateServerPanel operateServerPanel;
    @PostConstruct
    public void init() {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),"operate window"));

    }

    public void loadOperateServerWindow(String name){

        this.removeAll();
        this.setLayout(new BorderLayout());
        operateServerPanel.setTitle(name);
        this.add(operateServerPanel, BorderLayout.CENTER);
        this.updateUI();
    }
}
