package com.shell.manager.ui.panel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class MainPanelWindow  extends JFrame{

    @Autowired
    private LeftServerListPanel leftServerListPanel;

    @Autowired
    private OperateContainerPanel operateContainerPanel;

    @PostConstruct
    private void init() throws Exception {


        add(leftServerListPanel, BorderLayout.WEST);
        add(operateContainerPanel, BorderLayout.CENTER);

        this.setTitle("shell manager v1.0 auther@wangliang(email:13889463037@163.com)");
        getRootPane().setBorder(
                BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GRAY));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width / 2;
        int screenHeight = screenSize.height / 2;

        setMinimumSize(new Dimension(screenWidth - 700, screenHeight - 700));
        setSize(new Dimension(screenSize.width,screenSize.height-100));
        setResizable(true);
        setVisible(true);


        setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

}
