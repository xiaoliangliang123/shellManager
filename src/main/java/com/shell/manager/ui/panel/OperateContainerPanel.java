package com.shell.manager.ui.panel;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class OperateContainerPanel extends JPanel {

    @PostConstruct
    public void init() {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(),"operate window"));
    }
}
