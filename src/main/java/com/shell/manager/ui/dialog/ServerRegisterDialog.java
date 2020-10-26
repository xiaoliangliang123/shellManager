package com.shell.manager.ui.dialog;

import com.shell.manager.ui.listener.JTextFieldHintListener;

import javax.swing.*;
import java.awt.*;

public class ServerRegisterDialog extends JDialog {

    public ServerRegisterDialog(Frame frame) {
        super(frame, true);
    }


    public static ServerRegisterDialog buildDialog(String title, String content, Component component, Frame frame, OnConfirmCallBack onConfirmCallBack) {

        ServerRegisterDialog dialog = new ServerRegisterDialog(frame);

        dialog.setSize(250, 150);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(component);

        //设置标题
        dialog.setTitle(title);

        JTextField titleLabel = new JTextField(content, JTextField.CENTER);
        titleLabel.setHorizontalAlignment(JTextField.CENTER);
        titleLabel.addFocusListener(new JTextFieldHintListener(titleLabel, "输入名称"));
        JTextField ipLabel = new JTextField(content, JTextField.CENTER);
        ipLabel.setHorizontalAlignment(JTextField.CENTER);
        ipLabel.addFocusListener(new JTextFieldHintListener(ipLabel, "输入server ip"));
        JTextField usernameLabel = new JTextField(content, JTextField.CENTER);
        usernameLabel.setHorizontalAlignment(JTextField.CENTER);
        usernameLabel.addFocusListener(new JTextFieldHintListener(usernameLabel, "输入username"));
        JTextField passwordLabel = new JTextField(content, JTextField.CENTER);
        passwordLabel.setHorizontalAlignment(JTextField.CENTER);
        passwordLabel.addFocusListener(new JTextFieldHintListener(passwordLabel, "输入password"));

        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(event -> {
            try {
                onConfirmCallBack.confirm(titleLabel.getText(),ipLabel.getText(),usernameLabel.getText(),passwordLabel.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        // 添加组件到面板

        panel.add(titleLabel);
        panel.add(ipLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);
        panel.add(okBtn);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);

        return dialog;
    }


    public interface OnConfirmCallBack {

        void confirm(String name,String ip,String username,String password) throws Exception;
    }
}
