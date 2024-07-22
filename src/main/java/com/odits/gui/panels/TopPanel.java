package com.odits.gui.panels;

import com.odits.gui.components.CustomMenuItem;
import com.odits.utils.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import static com.odits.utils.IconLoader.darkMode;

public class TopPanel extends JPanel {
    private MainPanel mainPanel;
    private boolean iconView = true;
    private static JLabel dirLabel = new JLabel(System.getProperty("user.dir"));
    private JButton homeButton = new JButton();
    private JButton parentButton = new JButton();
    private JButton copyButton = new JButton();
    private JToolBar toolBar = new JToolBar();
    private TopPanelListener topPanelListener;
    public TopPanel(MainPanel mainPanel) {
        super();
        this.mainPanel = mainPanel;
        topPanelListener = new TopPanelListener(mainPanel);

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
        darkViewButton.setSelected(false);
        darkViewButton.addActionListener(e -> {
            darkMode = darkViewButton.isSelected();
            setTheme(darkMode);
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

        if (dark) {
            homeIcon = IconLoader.loadIcon("/Icons/Home-icon-dark.png");
            homeIcon.setImage(homeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            homeButton.setIcon(homeIcon);

            parentIcon = IconLoader.loadIcon("/Icons/back-arrow-dark.png");
            parentIcon.setImage(parentIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            parentButton.setIcon(parentIcon);

            copyIcon = IconLoader.loadIcon("/Icons/copy-icon-dark.png");
        } else {
            homeIcon = IconLoader.loadIcon("/Icons/Home-icon.png");
            homeIcon.setImage(homeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            homeButton.setIcon(homeIcon);

            parentIcon = IconLoader.loadIcon("/Icons/back-arrow.png");
            parentIcon.setImage(parentIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            parentButton.setIcon(parentIcon);

            copyIcon = IconLoader.loadIcon("/Icons/copy.png");
        }
        copyIcon.setImage(copyIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        copyButton.setIcon(copyIcon);
        repaint();
    }

    public void addButtons() {
        homeButton.setActionCommand("Home");
        homeButton.addActionListener(topPanelListener);


        parentButton.setActionCommand("Parent");
        parentButton.addActionListener(topPanelListener);

        copyButton.setActionCommand("Copy");
        copyButton.addActionListener(topPanelListener);
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
                    JOptionPane.showInputDialog(mainPanel, "Type the name of the new Directory", "Enter name", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case "Tree":
                mainPanel.getTreeScrollPane().setVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
                mainPanel.repaint();
                break;
            case "Home":
                mainPanel.currentDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
                mainPanel.reloadViewPanel(mainPanel.currentDirectory.toString());
                mainPanel.reloadTree(mainPanel.currentDirectory);
                break;
            case "Parent":
                mainPanel.reloadViewPanel(mainPanel.getCurrentDirectory().getParent());
                mainPanel.reloadTree(mainPanel.getCurrentDirectory().getParentFile());
                break;
            case "Copy":
                String copyFileName = JOptionPane.showInputDialog(mainPanel, "Enter the name of the file to copy");
                File fileToCopy = new File(mainPanel.getCurrentDirectory().getAbsolutePath() + File.separator + copyFileName);
                System.out.println(fileToCopy);
                break;
        }
    }
}
