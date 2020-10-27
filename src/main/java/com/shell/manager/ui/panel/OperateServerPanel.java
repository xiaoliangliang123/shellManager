package com.shell.manager.ui.panel;

import com.shell.manager.config.SSHAgent;
import com.shell.manager.config.SpringContextUtil;
import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.data.model.Server;
import com.shell.manager.ui.listener.UIUpdateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataUnit;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

@Component
public class OperateServerPanel extends JPanel implements KeyListener ,UIUpdateListener.UIUpdateActioner,MouseMotionListener {

    private String title ;

    private JTextArea shellArea = new JTextArea();
    private SSHAgent sshAgent = new SSHAgent();
    private DatabaseUtil databaseUtil;

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
        requestFocus();
        shellArea.setLineWrap(true);
        shellArea.addKeyListener(this);
        sshAgent.setUiUpdateActioner(this);
        this.setVisible(true);
    }

    public void connectSSH(String nodeName) throws Exception {
        databaseUtil =(DatabaseUtil) SpringContextUtil.getBean(DatabaseUtil.class);
        Server server =  databaseUtil.getServerRespository().findByName(nodeName).get();
        sshAgent.initSession(server.getIp(),server.getUsername(),server.getPassword());

    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override // 按下
    public void keyPressed(KeyEvent e) {
        System.out.print("按下："+KeyEvent.getKeyText(e.getKeyCode()) + "\n");
    }
    @Override // 松开
    public void keyReleased(KeyEvent e) {
        System.out.print("松开：" + KeyEvent.getKeyText(e.getKeyCode()) + "\n");

    }
    @Override // 输入的内容
    public void keyTyped(KeyEvent e) {
        System.out.print("输入：" + e.getKeyChar() + "\n");
    }

    @Override
    public void doUpdate(String content) {
        //System.out.print(content);
        shellArea.append(content+"\n\r");
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean status = this.requestFocusInWindow();
    }
}
