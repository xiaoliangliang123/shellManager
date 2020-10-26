package com.shell.manager.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * @author:WANGLIANG(XIAO)
 * @Date: 2019/6/28 17:16
 * @Description :
 */
public class OneInputDialog extends JDialog {

    public OneInputDialog(Frame frame){
         super(frame,true);
    }


    public static OneInputDialog buildDialog(String title,String content,Component component,Frame frame,OnConfirmCallBack onConfirmCallBack){

        OneInputDialog dialog = new OneInputDialog(frame);

        dialog.setSize(250, 150);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(component);

        //设置标题
        dialog.setTitle(title);

        // 创建一个标签显示消息内容
        JTextField messageLabel = new JTextField(content,JTextField.CENTER);
        messageLabel.setHorizontalAlignment(JTextField.CENTER);
        // 创建一个按钮用于关闭对话框
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(event -> {
            try {
                onConfirmCallBack.confirm(messageLabel.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        // 添加组件到面板
        panel.add(messageLabel,BorderLayout.CENTER);
        panel.add(okBtn,BorderLayout.SOUTH);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);

        return dialog;
    }


    public  interface OnConfirmCallBack {

        void confirm(String content) throws Exception;
    }

}

