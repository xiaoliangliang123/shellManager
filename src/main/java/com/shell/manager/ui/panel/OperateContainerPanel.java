package com.shell.manager.ui.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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
        addTab(name,operateServerPanel);
        operateServerPanel.connectSSH(name);
        this.updateUI();
    }

    public void startOutputServerByName(String currentNodeName) throws IOException {
        int count = getComponentCount();
        for(int i = 0 ; i < count;i++){
            java.awt.Component component = getComponent(i);
            if(getComponentAt(i) instanceof OperateServerPanel){
                OperateServerPanel operateServerPanel =  (OperateServerPanel)(getComponentAt(i));
                if(operateServerPanel.getTitle().equals(currentNodeName)) {
                    operateServerPanel.startOutputStream();
                }
            }
        }
    }

    public void stopOutputServerByName(String currentNodeName) {
        int count = getComponentCount();
        for(int i = 0 ; i < count;i++){
            java.awt.Component component = getComponent(i);
            if(getComponentAt(i) instanceof OperateServerPanel){
                OperateServerPanel operateServerPanel =  (OperateServerPanel)(getComponentAt(i));
                if(operateServerPanel.getTitle().equals(currentNodeName)) {
                    operateServerPanel.stopOutputStream();
                }
            }
        }
    }
}
