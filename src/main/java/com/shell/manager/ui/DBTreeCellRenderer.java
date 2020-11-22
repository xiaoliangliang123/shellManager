package com.shell.manager.ui;


import com.shell.manager.config.DataUtil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * @author:WANGLIANG(XIAO)
 * @Date: 2019/6/4 19:49
 * @Description :
 */
public class DBTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded, boolean leaf, int row,
                                                  boolean hasFocus) {

        //执行父类原型操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        setText(value.toString());

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if(node.getLevel() ==0) {
            this.setIcon(new ImageIcon(DataUtil.getImgURLFromResourcesByName("img_servers.png")));
        }else if(node.getLevel() ==1){
            this.setIcon(new ImageIcon(DataUtil.getImgURLFromResourcesByName("img_group.png")));
        }
        else if(node.getLevel() ==2){
            this.setIcon(new ImageIcon(DataUtil.getImgURLFromResourcesByName("img_server.png")));
        }

        return this;
    }


}
