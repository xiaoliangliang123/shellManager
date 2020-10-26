package com.shell.manager.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * @author:WANGLIANG(XIAO)
 * @Date: 2019/5/30 14:06
 * @Description :
 */
public class MsgDialogUtil {

    public static void showDialogMsg(Component com,Object msg){
        JOptionPane.showMessageDialog(com, msg,"信息消息",JOptionPane.INFORMATION_MESSAGE);
    }
}
