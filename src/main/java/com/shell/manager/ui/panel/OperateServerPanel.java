package com.shell.manager.ui.panel;

import com.shell.manager.config.SSHAgent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

@Component
public class OperateServerPanel extends JPanel implements KeyListener {

    private String title ;

    private JTextArea shellArea = new JTextArea();

    @PostConstruct
    public void init() throws IOException {
        JScrollPane jScrollPane=new JScrollPane(shellArea);
        this.setBorder(BorderFactory.createTitledBorder(title));
        //this.setPreferredSize(new Dimension(430, 380));
        this.setLayout(new BorderLayout());
        //shellArea.setPreferredSize(new Dimension(430,200));
        shellArea.setEditable(true);
        shellArea.setLineWrap(true);
        this.add(jScrollPane,BorderLayout.CENTER);
        shellArea.requestFocus();
        //SSHAgent.run();
        this.addKeyListener(this);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
