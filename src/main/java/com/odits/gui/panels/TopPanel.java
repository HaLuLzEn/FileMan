package com.odits.gui.panels;

import com.odits.Main;
import com.odits.gui.components.CustomMenuItem;
import com.odits.utils.IconLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class TopPanel extends JPanel {
    private MainPanel mainPanel;
    private boolean iconView = true;
    public TopPanel(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
        setLayout(new GridLayout(2, 1));
        TopPanelListener topPanelListener = new TopPanelListener(mainPanel);

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


        fileMenu.add(newSubMenu, 0);
        fileMenu.add(openSubMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);



        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton parentButton = new JButton();
        ImageIcon parentIcon = IconLoader.loadIcon("/Icons/back-arrow.png");
        parentIcon.setImage(parentIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        parentButton.setIcon(parentIcon);
        parentButton.setActionCommand("Parent");
        parentButton.addActionListener(topPanelListener);
        toolBar.add(parentButton);

        JButton copyButton = new JButton();
        ImageIcon copyIcon = IconLoader.loadIcon("/Icons/copy.png");
        copyIcon.setImage(copyIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        copyButton.setIcon(copyIcon);
        copyButton.setActionCommand("Copy");
        toolBar.add(copyButton);

        this.add(menuBar, 0);
        this.add(toolBar, 1);
    }
}

class TopPanelListener implements ActionListener {
    private MainPanel mainPanel;

    public TopPanelListener (MainPanel mainPanel) {
        this.mainPanel = mainPanel;
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
                    fileChooser.showDialog(mainPanel, "Open");
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
                    fileChooser.showDialog(mainPanel, "Open");
                    mainPanel.reloadViewPanel(fileChooser.getSelectedFile().getAbsolutePath());
                } else {
                    System.out.println("New Folder");
                }
                break;
            case "Tree":
                mainPanel.getTreeScrollPane().setVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
                break;
            case "Parent":
                mainPanel.reloadViewPanel(mainPanel.getCurrentDirectory().getParent());
                break;
            case "Copy":
                String copyFileName = JOptionPane.showInputDialog(mainPanel, "Enter the name of the file to copy");
                File fileToCopy = new File(mainPanel.getCurrentDirectory().getAbsolutePath() + File.separator + copyFileName);
                System.out.println(fileToCopy);
                break;
        }
    }
}
