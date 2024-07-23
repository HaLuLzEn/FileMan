package com.odits.gui.panels;

import com.odits.gui.components.CustomMenuItem;
import com.odits.gui.components.CustomPopupMenu;
import com.odits.gui.components.CustomPopupMenuEmpty;
import com.odits.gui.components.IconLabel;
import com.odits.utils.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static com.odits.Main.darkMode;

public class ViewPanel extends JPanel {
    private static final int ICON_SIZE = 32; // Size of the icon
    private static final int ROWS = 5; // Rows set to 0 for variable number of rows
    private static final int COLS = 4; // Adjust based on your needs
    private IconLabel selectedLabel = new IconLabel();
    private File currentFile;
    private boolean iconView = true;

    public ViewPanel(String directoryPath, MainPanel mainPanel) {
        setLayout(new GridLayout(ROWS, COLS));
        File dir = new File(directoryPath);
        this.setComponentPopupMenu(new CustomPopupMenuEmpty());


        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Create an icon and label for each file
                    ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
                    if (file.isDirectory()) {
                        if (darkMode) {
                            icon = IconLoader.loadIcon("/Icons/folder-icon-dark.png");
                            icon.setImage(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
                        } else {
                            icon = IconLoader.loadIcon("/Icons/folder-icon.png");
                            icon.setImage(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH));
                        }
                    }
                    IconLabel ilabel = new IconLabel(file.getName(), new ImageIcon(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)), JLabel.CENTER, file.getAbsolutePath());
                    ilabel.setVerticalTextPosition(JLabel.BOTTOM);
                    ilabel.setHorizontalTextPosition(JLabel.CENTER);
                    ilabel.setComponentPopupMenu(new CustomPopupMenu(this, mainPanel));


                    ilabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (ilabel.isSelected()) {
                                try {
                                    if (file.isDirectory()) {
                                        mainPanel.reloadViewPanel(file.getAbsolutePath());
                                        mainPanel.reloadTree(file.getAbsoluteFile());
                                    } else {
                                        executeFile(file.getAbsolutePath());
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else if (selectedLabel == null && !ilabel.isSelected()) {
                                selectedLabel = ilabel;
                                ilabel.setSelected(true);
                            } else {
                                selectedLabel.setSelected(false);
                                selectedLabel.setBorder(null);
                                selectedLabel = ilabel;
                                if (darkMode)
                                    selectedLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                                 else
                                    selectedLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                                ilabel.setSelected(true);
                            }
                        }


                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (darkMode)
                                ilabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                            else
                                ilabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                            currentFile = new File(mainPanel.getCurrentDirectory() + ilabel.getText());
                            repaint();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            ilabel.setBorder(null);
                            selectedLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            repaint();
                        }

                        @Override
                        public void mouseDragged(MouseEvent e) {

                        }
                    });
                    add(ilabel);
                }
            }
        }
    }

    public String getSelectedIconPath() {
        return selectedLabel.getPath();
    }

    public IconLabel getSelectedIconLabel() {
        return selectedLabel;
    }

    public void setSelectedIconLabel(IconLabel label) {
        selectedLabel = label;
    }

    public static void executeFile(String filePath) {
        System.out.println("Executing file: " + filePath);
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("cmd /c start \"\" \"" + filePath + "\"");
            } else {
                if (filePath.endsWith(".AppImage"))
                    Runtime.getRuntime().exec(filePath);
                else
                    Runtime.getRuntime().exec(new String[]{
                            "xdg-open", filePath
                    });
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while trying to execute the file.");
        }
    }

    public boolean isIconView() {
        return iconView;
    }

    public void setIconView(boolean iconView) {
        this.iconView = iconView;
    }
}