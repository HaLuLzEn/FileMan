package com.odits.gui.panels;

import com.odits.gui.components.CustomMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            case "Open":
                // Implement open action
                break;
            case "Exit":
                System.exit(0);
                break;
            case "File": {
                if (((CustomMenuItem) e.getSource()).isOpen()) {
                    System.out.println("Open File");
                } else {
                    System.out.println("New File");
                }
                    break;
            }
            case "Folder":
                // Implement new folder creation action
                break;
            case "Tree":
                mainPanel.getTreeScrollPane().setVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
                break;
        }
    }
}
