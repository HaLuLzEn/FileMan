package com.odits.gui.panels;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Paths;

public class MainPanel extends JPanel {
    // Components to be accessed
    JTree tree = new JTree();
    JScrollPane treeScrollPane = new JScrollPane(tree);
    public MainPanel() {
        super();
        setLayout(new BorderLayout());

        treeScrollPane.setPreferredSize(new Dimension(175, 0));

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Test");
        rootNode = setDirectoryContentToNode(rootNode);

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);

        this.add(treeScrollPane, BorderLayout.WEST);
        this.add(new ViewPanel(), BorderLayout.CENTER);
        this.add(new TopPanel(this), BorderLayout.NORTH);
    }

    public JScrollPane getTreeScrollPane() {
        return treeScrollPane;
    }

    public DefaultMutableTreeNode setDirectoryContentToNode(DefaultMutableTreeNode node) {
        File dir = new File(node.getUserObject().toString());
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getName());
                    if (file.isDirectory()) {
                        // Recursively add directory contents
                        setDirectoryContentToNode(childNode);
                    }
                    node.add(childNode);
                }
            }
        }
        return node;
    }
}
