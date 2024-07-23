package com.odits.gui.panels;

import com.odits.gui.frames.MainFrame;
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
    File currentDirectory = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
    ViewPanel viewPanel;
    JScrollPane viewScrollPane;
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public MainPanel(MainFrame mainFrame) {
        super();
        GlobalKeyListener.initGlobalKeyListener(this);
        setLayout(new BorderLayout());
        tree.setLayout(new BorderLayout());

        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            currentDirectory = new File(currentDirectory.getParentFile().getParent());
        }

        treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        DefaultTreeModel treeModel = createTreeModel(currentDirectory);
        viewPanel = new ViewPanel(currentDirectory.getAbsolutePath(), this);
        viewScrollPane = new JScrollPane(viewPanel);
        tree.setModel(treeModel);
        tree.addTreeSelectionListener(e -> {
            TreePath path = e.getPath();
            if (viewPanel.getSelectedIconPath() != null) {
                viewPanel.getSelectedIconLabel().setSelected(false);
                viewPanel.setSelectedIconLabel(null);
            }
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            File file = (File) node.getUserObject();
            if (file.isDirectory()) {
                loadContents(file, node);
                reloadViewPanel(file.getPath());
            }
        });

        tree.setDragEnabled(true);
        tree.setCellRenderer(new FileCellRenderer());
        System.out.println(tree.getLayout());

        splitPane.add(treeScrollPane);
        splitPane.add(viewScrollPane);
        splitPane.setDividerLocation(200);

        this.add(splitPane);
        this.add(new TopPanel(this, viewPanel, mainFrame), BorderLayout.NORTH);
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
                    if (childFile.isDirectory()) {
                        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childFile);
                        node.add(childNode);
                    }
                }
            }
        }
    }

    private void loadContents(File file, DefaultMutableTreeNode rootNode) {
        File[] contents = file.listFiles();

        if (contents == null || contents.length == 0) {
            System.out.println("Directory is empty");
        } else {
            for (File f : contents) {
                if (f.isDirectory()) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(f);
                    rootNode.add(node);
                }
            }
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

    public JSplitPane getSplitPane() {
        return splitPane;
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
                ImageIcon icon;
                if (isDrive(currentNode)) {
                    icon = IconLoader.loadIcon("/Icons/drive-icon-dark.png");
                } else {
                    icon = IconLoader.loadIcon("/Icons/folder-icon-dark.png");
                }
                icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(icon);
            } else {
                ImageIcon icon;
                if (isDrive(currentNode)) {
                    icon = IconLoader.loadIcon("/Icons/drive-icon.png");
                } else {
                    icon = IconLoader.loadIcon("/Icons/folder-icon.png");
                }
                icon.setImage(icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
                setIcon(icon);
            }
        } else {
            setText(currentNode.getAbsoluteFile().getName());
        }
        return this;
    }

    private boolean isDrive(File file) {
        File[] roots = File.listRoots();
        for (File root : roots) {
            if (root.equals(file)) {
                return true;
            }
        }
        return false;
    }
}