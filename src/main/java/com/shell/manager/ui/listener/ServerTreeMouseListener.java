package com.shell.manager.ui.listener;

import com.shell.manager.config.GenerateUtil;
import com.shell.manager.config.SpringContextUtil;
import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.data.model.Group;
import com.shell.manager.data.model.Server;
import com.shell.manager.ui.dialog.MsgDialogUtil;
import com.shell.manager.ui.dialog.OneInputDialog;
import com.shell.manager.ui.dialog.ServerRegisterDialog;
import com.shell.manager.ui.listener.event.RefreshServerTreeEvent;
import com.shell.manager.ui.panel.MainPanelWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.text.html.Option;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Optional;

public class ServerTreeMouseListener implements MouseListener, ActionListener {

    private JPopupMenu groupMenu;
    private JPopupMenu serverMenu;
    private JMenuItem addGroupItem;
    private JMenuItem addServerItem;

    private JTree tree;
    private String currentNodeName;

    public ServerTreeMouseListener(JTree jTree) {
        this.tree = jTree;
        groupMenu = new JPopupMenu();
        serverMenu = new JPopupMenu();
        addGroupItem = new JMenuItem("add group");
        addServerItem = new JMenuItem("add server");
        addGroupItem.addActionListener(this);
        addServerItem.addActionListener(this);
        groupMenu.add(addGroupItem);
        serverMenu.add(addServerItem);

        addGroupItem.addActionListener(event -> {

            try {
                OneInputDialog.buildDialog("add group", "", tree, null
                        , name -> {
                            DatabaseUtil databaseUtil = (DatabaseUtil) SpringContextUtil.getBean(DatabaseUtil.class);
                            Optional<Group> groupOptional = databaseUtil.getGroupRespository().findByName(name);
                            if (groupOptional.isPresent()) {
                                MsgDialogUtil.showDialogMsg(tree, "组名已存在，请重新输入");
                            } else {
                                String uuid = GenerateUtil.uuid();
                                Group group = new Group(uuid, name);
                                databaseUtil.getGroupRespository().saveOrUpdate(group);
                                SpringContextUtil.publishEvent(new RefreshServerTreeEvent(group));
                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        addServerItem.addActionListener(event -> {

            try {
                ServerRegisterDialog.buildDialog("add server", "", tree, null
                        , (name,ip, username, password) -> {
                            DatabaseUtil databaseUtil =  (DatabaseUtil)SpringContextUtil.getBean(DatabaseUtil.class);
                            Optional<Server> serverOptional = databaseUtil.getServerRespository().findByName(name);
                            if(serverOptional.isPresent()){
                                MsgDialogUtil.showDialogMsg(tree, "server名称已存在，请重新输入");
                            }else {
                                String uuid = GenerateUtil.uuid();
                                Server server = new Server(uuid,currentNodeName,name,ip,username,password);
                                databaseUtil.getServerRespository().saveOrUpdate(server);
                                SpringContextUtil.publishEvent(new RefreshServerTreeEvent(server));

                            }
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (ObjectUtils.isEmpty(node) || e.getButton() != 3) {
            return;
        }
        currentNodeName = node.toString();
        if (Integer.compare(node.getLevel(), 0) == 0) {
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) {
                return;
            }
            tree.setSelectionPath(path);

            if (e.getButton() == 3) {
                groupMenu.show(tree, e.getX(), e.getY());
            }

        } else if (Integer.compare(node.getLevel(), 1) == 0) {
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) {
                return;
            }
            tree.setSelectionPath(path);

            if (e.getButton() == 3) {
                serverMenu.show(tree, e.getX(), e.getY());
            }
        } else {
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
