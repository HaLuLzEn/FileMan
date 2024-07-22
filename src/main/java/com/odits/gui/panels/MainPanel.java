package com.odits.gui.panels;

import com.odits.listeners.GlobalKeyListener;
import com.odits.utils.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.odits.utils.IconLoader.darkMode;


public class MainPanel extends JPanel {
    JTree tree = new JTree();
    JScrollPane treeScrollPane = new JScrollPane(tree);
    ViewPanel viewPanel = new ViewPanel(System.getProperty("user.dir"), this);
    JScrollPane viewScrollPane = new JScrollPane(viewPanel);
    File currentDirectory = new File(System.getProperty("user.dir"));
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public MainPanel() {
        super();
        GlobalKeyListener.initGlobalKeyListener(this);
        setLayout(new BorderLayout());

        treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        DefaultTreeModel treeModel = createTreeModel(new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath()));
        tree.setModel(treeModel);
        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            File file = (File) node.getUserObject();
            if (file.isDirectory()) {
                loadContents(file, node);
                reloadViewPanel(file.getPath());
            }
        });

        tree.setDragEnabled(true);
        tree.setCellRenderer(new FileCellRenderer());

        splitPane.add(treeScrollPane);
        splitPane.add(viewScrollPane);

        this.add(splitPane);
        this.add(new TopPanel(this), BorderLayout.NORTH);
    }

    public JScrollPane getTreeScrollPane() {
        return treeScrollPane;
    }

    public void reloadTree(File directroy) {
        if (directroy != null) {
            DefaultTreeModel treeModel = createTreeModel(directroy);
            tree.setModel(treeModel);
            repaint();
        }
    }

    public void reloadViewPanel(String directoryPath) {
        if (directoryPath != null) {
            currentDirectory = new File(directoryPath);
            splitPane.remove(viewScrollPane);
            viewPanel = new ViewPanel(directoryPath, this);
            viewScrollPane = new JScrollPane(viewPanel);
            TopPanel.reloadLabel(directoryPath);
            splitPane.add(viewScrollPane);
            splitPane.setDividerLocation(200);
            this.revalidate();
            this.repaint();
        }
    }

    public File getCurrentDirectory() {
        return new File(currentDirectory.getAbsolutePath());
    }

    private DefaultTreeModel createTreeModel(File rootDirectory) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDirectory);
        createNodes(rootNode, rootDirectory);
        return new DefaultTreeModel(rootNode);
    }

    private void createNodes(DefaultMutableTreeNode node, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childFile);
                    node.add(childNode);
                }
            }
        }
    }

    private void loadContents(File file, DefaultMutableTreeNode rootNode) {
        File[] contents = file.listFiles();

        assert contents != null;
        for (File f : contents) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
            rootNode.add(node);

        }
    }

    public List<String> listContents(File file) {
        List<String> contents = new ArrayList<String>();
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            contents.add(f.getName());
        }
        return contents;
    }
}

class FileCellRenderer extends JLabel implements TreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        File currentNode = (File) node.getUserObject();
        if (currentNode != null && currentNode.isDirectory()) {
            setText(currentNode.getName());
            if (darkMode) {
                ImageIcon icon = IconLoader.loadIcon("/Icons/folder-icon-dark.png");
                icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(icon);
            } else {
                ImageIcon icon = IconLoader.loadIcon("/Icons/folder-icon.png");
                icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(icon);
            }
        } else {
            setText(currentNode.getAbsoluteFile().getName());
        }
        return this;
    }
}