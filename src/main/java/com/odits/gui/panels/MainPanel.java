package com.odits.gui.panels;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class MainPanel extends JPanel implements KeyListener {
    // Components to be accessed
    JTree tree = new JTree();
    JScrollPane treeScrollPane = new JScrollPane(tree);
    ViewPanel viewPanel = new ViewPanel(System.getProperty("user.dir"), this);
    JScrollPane viewScrollPane = new JScrollPane(viewPanel);
    File currentDirectory = new File(System.getProperty("user.dir"));
    public MainPanel() {
        super();
        setLayout(new BorderLayout());

        treeScrollPane.setPreferredSize(new Dimension(175, 0));

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Test");
        rootNode = setDirectoryContentToNode(rootNode);

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);

        this.add(treeScrollPane, BorderLayout.WEST);
        this.add(viewScrollPane, BorderLayout.CENTER);
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

    public void reloadTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Test");
        rootNode = setDirectoryContentToNode(rootNode);
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        tree.setModel(treeModel);
    }

    public void reloadViewPanel(String directoryPath) {
        currentDirectory = new File(directoryPath);
        this.remove(viewScrollPane);
        viewPanel = new ViewPanel(directoryPath, this);
        viewScrollPane = new JScrollPane(viewPanel);
        this.add(viewScrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public File getCurrentDirectory() {
        return new File(currentDirectory.getAbsolutePath());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
