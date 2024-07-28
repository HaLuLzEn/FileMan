package com.odits.gui.panels;

import com.odits.gui.components.CustomMenuItem;
import com.odits.gui.frames.MainFrame;
import com.odits.utils.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static com.odits.Main.darkMode;

public class TopPanel extends JPanel {
    private boolean iconView = true;
    private static JLabel dirLabel = new JLabel();
    private JButton homeButton = new JButton();
    private JButton parentButton = new JButton();
    private JButton copyButton = new JButton();
    private JButton refreshButton = new JButton();
    private JToolBar toolBar = new JToolBar();
    private TopPanelListener topPanelListener;

    public TopPanel(MainPanel mainPanel, ViewPanel viewPanel, MainFrame mainFrame) {
        super();
        topPanelListener = new TopPanelListener(mainPanel, viewPanel);

        setLayout(new GridLayout(2, 1));


        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenu openSubMenu = new JMenu("Open");
        CustomMenuItem openFileMenuItem = new CustomMenuItem("File", true);
        openFileMenuItem.addActionListener(topPanelListener);
        CustomMenuItem openFolderMenuItem = new CustomMenuItem("Folder", true);
        openFolderMenuItem.addActionListener(topPanelListener);
        openSubMenu.add(openFileMenuItem);
        openSubMenu.add(openFolderMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(topPanelListener);

        JMenu newSubMenu = new JMenu("New");
        CustomMenuItem newFileMenuItem = new CustomMenuItem("File", false);
        newFileMenuItem.addActionListener(topPanelListener);
        CustomMenuItem newFolderMenuItem = new CustomMenuItem("Folder", false);
        newFolderMenuItem.addActionListener(topPanelListener);
        newSubMenu.add(newFileMenuItem);
        newSubMenu.add(newFolderMenuItem);

        JMenu viewMenu = new JMenu("View");
        JCheckBoxMenuItem treeCheckBoxMI = new JCheckBoxMenuItem("Tree");
        treeCheckBoxMI.addActionListener(topPanelListener);
        treeCheckBoxMI.setSelected(mainPanel.tree.isVisible());
        viewMenu.add(treeCheckBoxMI);

        ButtonGroup viewButtonGroup = new ButtonGroup();
        JRadioButtonMenuItem iconViewCheckBoxMI = new JRadioButtonMenuItem("Icon view");
        iconViewCheckBoxMI.addActionListener(topPanelListener);
        iconViewCheckBoxMI.setSelected(iconView);
        viewButtonGroup.add(iconViewCheckBoxMI);
        viewMenu.add(iconViewCheckBoxMI);
        JRadioButtonMenuItem listViewCheckBoxMI = new JRadioButtonMenuItem("List view");
        listViewCheckBoxMI.addActionListener(topPanelListener);
        listViewCheckBoxMI.setSelected(!iconView);
        viewButtonGroup.add(listViewCheckBoxMI);
        viewMenu.add(listViewCheckBoxMI);

        JCheckBoxMenuItem darkViewButton = new JCheckBoxMenuItem("Dark Theme");
        darkViewButton.setSelected(darkMode);
        darkViewButton.addActionListener(e -> {
            darkMode = darkViewButton.isSelected();
            mainFrame.setTheme(darkMode);
            setTheme(darkMode);
            refreshButton.doClick();
        });
        viewMenu.add(darkViewButton);

        fileMenu.add(newSubMenu, 0);
        fileMenu.add(openSubMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);


        toolBar.setFloatable(false);

        setTheme(darkViewButton.isSelected());
        addButtons();
        toolBar.add(homeButton);
        toolBar.add(parentButton);
        toolBar.add(copyButton);
        toolBar.add(refreshButton);

        dirLabel.setText(mainPanel.getCurrentDirectory().toString());
        toolBar.add(dirLabel);

        this.add(menuBar, 0);
        this.add(toolBar, 1);
    }

    public static void reloadLabel(String text) {
        dirLabel.setText(text);
    }

    public void setTheme(boolean dark) {
        ImageIcon homeIcon;
        ImageIcon parentIcon;
        ImageIcon copyIcon;
        ImageIcon refreshIcon;

        if (dark) {
            homeIcon = IconLoader.loadIcon("/Icons/Home-icon-dark.png");

            parentIcon = IconLoader.loadIcon("/Icons/back-arrow-dark.png");

            copyIcon = IconLoader.loadIcon("/Icons/copy-icon-dark.png");

            refreshIcon = IconLoader.loadIcon("/Icons/refresh-icon-dark.png");
        } else {
            homeIcon = IconLoader.loadIcon("/Icons/Home-icon.png");

            parentIcon = IconLoader.loadIcon("/Icons/back-arrow.png");

            copyIcon = IconLoader.loadIcon("/Icons/copy.png");

            refreshIcon = IconLoader.loadIcon("/Icons/refresh-icon.png");
        }
        homeIcon.setImage(homeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        homeButton.setIcon(homeIcon);
        parentIcon.setImage(parentIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        parentButton.setIcon(parentIcon);
        copyIcon.setImage(copyIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        copyButton.setIcon(copyIcon);
        refreshIcon.setImage(refreshIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        refreshButton.setIcon(refreshIcon);
        repaint();
    }

    public void addButtons() {
        homeButton.setActionCommand("Home");
        homeButton.addActionListener(topPanelListener);


        parentButton.setActionCommand("Parent");
        parentButton.addActionListener(topPanelListener);

        copyButton.setActionCommand("Copy");
        copyButton.addActionListener(topPanelListener);

        refreshButton.setActionCommand("Refresh");
        refreshButton.addActionListener(topPanelListener);
    }
}

class TopPanelListener implements ActionListener {
    private final MainPanel MAIN_PANEL;
    private final ViewPanel VIEW_PANEL;

    public TopPanelListener(MainPanel mainPanel, ViewPanel viewPanel) {
        this.MAIN_PANEL = mainPanel;
        this.VIEW_PANEL = viewPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Exit":
                System.exit(0);
                break;
            case "File": {
                if (((CustomMenuItem) e.getSource()).isOpen()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.showDialog(MAIN_PANEL, "Open");
                    try {
                        Runtime.getRuntime().exec(new String[]{"xdg-open", fileChooser.getSelectedFile().getAbsolutePath()});
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("New File");
                }
                break;
            }
            case "Folder":
                if (((CustomMenuItem) e.getSource()).isOpen()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.showDialog(MAIN_PANEL, "Open");
                    MAIN_PANEL.reloadViewPanel(fileChooser.getSelectedFile().getAbsolutePath());
                } else {
                    JOptionPane.showInputDialog(MAIN_PANEL, "Type the name of the new Directory", "Enter name", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case "Tree":
                boolean selected = ((JCheckBoxMenuItem) e.getSource()).isSelected();
                MAIN_PANEL.getTreeScrollPane().setVisible(selected);
                if (selected) MAIN_PANEL.getSplitPane().setDividerLocation(0);
                else {
                    MAIN_PANEL.getSplitPane().setDividerLocation(200);
                    MAIN_PANEL.reloadViewPanel(MAIN_PANEL.getCurrentDirectory().getAbsolutePath());
                    MAIN_PANEL.reloadTree(MAIN_PANEL.getCurrentDirectory());
                }

                MAIN_PANEL.repaint();
                MAIN_PANEL.revalidate();
                break;
            case "Home":
                    String os = System.getProperty("os.name").toLowerCase();
                    File currentDirectory;
                    if (os.contains("win")) {
                        currentDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getParentFile();
                        MAIN_PANEL.currentDirectory = new File(currentDirectory.getAbsolutePath());
                        MAIN_PANEL.reloadViewPanel(currentDirectory.getAbsolutePath());
                        MAIN_PANEL.reloadTree(currentDirectory);
                    } else if (os.contains("nux") || os.contains("nix")) {
                        currentDirectory = MAIN_PANEL.getCurrentDirectory();
                        MAIN_PANEL.currentDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
                        MAIN_PANEL.reloadViewPanel(currentDirectory.getAbsolutePath());
                        MAIN_PANEL.reloadTree(currentDirectory);
                    }
                break;
            case "Parent":
                if (MAIN_PANEL.currentDirectory.getParent() != null) {
                    File parentFile = MAIN_PANEL.getCurrentDirectory().getParentFile();
                    MAIN_PANEL.currentDirectory = parentFile;
                    MAIN_PANEL.reloadViewPanel(parentFile.getAbsolutePath());
                    MAIN_PANEL.reloadTree(parentFile);
                }
                break;
            case "Copy":
                if (!(VIEW_PANEL.getCurrentFile() == null)) {
                    System.out.println("Copying: " + VIEW_PANEL.getCurrentFile().getAbsolutePath());
                    String copyFileName = JOptionPane.showInputDialog(MAIN_PANEL, "Enter the name of the file to copy");
                    if (copyFileName == null) return;
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.showDialog(MAIN_PANEL, "Select Directory");
                    File dir = fileChooser.getSelectedFile();
                    File fileToCopy = new File(dir + File.separator + copyFileName);
                    System.out.println(fileToCopy);
                }
                break;
            case "Refresh":
                MAIN_PANEL.reloadViewPanel(MAIN_PANEL.getCurrentDirectory().getAbsolutePath());
                MAIN_PANEL.reloadTree(MAIN_PANEL.getCurrentDirectory());
                if (MAIN_PANEL.getSplitPane().isVisible()) MAIN_PANEL.getSplitPane().setDividerLocation(200);
                break;
        }
    }

    private void copyFile(File file, File destination) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    File newDestination = new File(destination, childFile.getName());
                    if (childFile.isDirectory()) {
                        newDestination.mkdir();
                    }
                    copyFile(childFile, newDestination);
                }
            }
        } else {

        }
    }
}
