package com.shell.manager.ui.panel;

import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.data.model.Group;
import com.shell.manager.data.model.Server;
import com.shell.manager.ui.listener.ServerTreeMouseListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

@Component
public class LeftServerListPanel extends JPanel {

    @Autowired
    private DatabaseUtil databaseUtil;
    private DefaultTreeModel defaultTreeModel;
    private JTree treeRoot = new JTree();


    public void reloadTree() throws Exception {

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("servers");
        List<Group> groups = databaseUtil.getGroupRespository().queryAll();
        for (Group group : groups) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group.getName());
            List<Server> servers = databaseUtil.getServerRespository().queryAllByParentName(groupNode.toString());
            for (Server server:servers) {
                DefaultMutableTreeNode serverNode = new DefaultMutableTreeNode(server.getName());
                groupNode.add(serverNode);
            }
            root.add(groupNode);
        }

        defaultTreeModel = new DefaultTreeModel(root);
        treeRoot.setModel(defaultTreeModel);
        treeRoot.updateUI();

        expandAll(treeRoot, new TreePath(root), true);
    }

    @PostConstruct
    public  void init() throws Exception {
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "servers"));
        reloadTree();
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension screenSize = kit.getScreenSize();
        JScrollPane jScrollPane1 = new JScrollPane();
        jScrollPane1.getViewport().add(treeRoot, null);
        jScrollPane1.setPreferredSize(new Dimension(430, screenSize.height - 200));
        add(jScrollPane1);

        treeRoot.addMouseListener(new ServerTreeMouseListener(treeRoot));
    }

    private static void expandAll(JTree tree, TreePath parent, boolean expand)
    {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0)
        {
            for (Enumeration e = node.children(); e.hasMoreElements();)
            {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        if (expand)
        {
            tree.expandPath(parent);
        } else
        {
            tree.collapsePath(parent);
        }
    }


}
