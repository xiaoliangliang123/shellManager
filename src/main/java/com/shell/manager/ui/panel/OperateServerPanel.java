package com.shell.manager.ui.panel;

import com.shell.manager.config.KeybordUtil;
import com.shell.manager.config.SSHAgent;
import com.shell.manager.config.SpringContextUtil;
import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.data.model.Server;
import com.shell.manager.ui.listener.UIUpdateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
import java.nio.CharBuffer;

public class OperateServerPanel extends JPanel implements KeyListener, UIUpdateListener.UIUpdateActioner, MouseMotionListener {

    private String title;

    private JTextArea shellArea = new JTextArea();
    private SSHAgent sshAgent = new SSHAgent();
    private DatabaseUtil databaseUtil;
    private StringBuilder keyBorder = new StringBuilder();

    public OperateServerPanel() {

        try {
            JScrollPane jScrollPane = new JScrollPane(shellArea);
            this.setBorder(BorderFactory.createTitledBorder(title));
            this.setLayout(new BorderLayout());
            shellArea.setEditable(true);
            shellArea.setLineWrap(true);
            this.add(jScrollPane, BorderLayout.CENTER);
            requestFocus();
            shellArea.addKeyListener(this);
            sshAgent.setUiUpdateActioner(this);
            shellArea.setBackground(Color.black);
            shellArea.setForeground(Color.WHITE);
            this.setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void connectSSH(String nodeName) throws Exception {
        databaseUtil = (DatabaseUtil) SpringContextUtil.getBean(DatabaseUtil.class);
        Server server = databaseUtil.getServerRespository().findByName(nodeName).get();
        sshAgent.initSession(nodeName, server.getIp(), server.getUsername(), server.getPassword());

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override // 按下
    public void keyPressed(KeyEvent e) {
        try {
//            sshAgent.execCommandNoneEntry("\n\r");
//            System.out.print("按下：" + e.getKeyCode() + "\n");
//            String keycode = KeyEvent.getKeyText(e.getKeyCode());
//            if (KeybordUtil.isEnter(keycode)) {
//                sshAgent.execCommandNoneEntry("\n\r");
//                //}
////                sshAgent.execCommand("");
////                //sshAgent.execCommand(keyBorder.toString());
////                System.out.print("命令：" + KeyEvent.getKeyText(e.getKeyCode()) + "\n");
//                keyBorder.setLength(0)

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override // 松开
    public void keyReleased(KeyEvent e) {

        System.out.print("松开：" + e.getKeyChar() + "\n");

    }

    @Override // 输入的内容
    public void keyTyped(KeyEvent e) {

        System.out.print("输入：" + e.getKeyChar() + "\n");
        char keycode = e.getKeyChar();

        try {
            sshAgent.execCommandNoneEntry(keycode + "");

//            if (KeybordUtil.isTabChar(e.getKeyChar())) {
//
//                sshAgent.execCommandNoneEntry("\t ");
//                sshAgent.execCommandNoneEntry("\f ");
//
////                sshAgent.execCommandNoneEntry(keycode + "");
////            }else {
////                //keyBorder.append(keycode);
////                keyBorder.setLength(0);
////                sshAgent.execCommandNoneEntry("\n\t");
////
//////                if (keyBorder.toString().endsWith("\t\t")) {
//////                    sshAgent.execCommandNoneEntry("\n\t");
//////                    keyBorder.setLength(0);
//////                }
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


        @Override
    public void doUpdate(String content) {
        shellArea.append(content + "\n");
        shellArea.setCaretPosition(shellArea.getText().length() - 1);


    }

    @Override
    public void removeLastLine() {
        shellArea.setRows(4);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean status = this.requestFocusInWindow();
    }


    public void startOutputStream() throws IOException {
        sshAgent.startOutputStream();
    }

    public void stopOutputStream() {
        sshAgent.stopOutputStream();
    }
}
