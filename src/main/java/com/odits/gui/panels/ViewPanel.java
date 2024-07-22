package com.odits.gui.panels;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;

public class ViewPanel extends JPanel {
    private static final int ICON_SIZE = 64; // Size of the icon
    private static final int ROWS = 5; // Rows set to 0 for variable number of rows
    private static final int COLS = 4; // Adjust based on your needs
    private File currentFile;

    public ViewPanel(String directoryPath, MainPanel panel) {
        setLayout(new GridLayout(ROWS, COLS));
        File dir = new File(directoryPath);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    // Create an icon and label for each file
                    ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
                    JLabel label = new JLabel(file.getName(), new ImageIcon(icon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH)), JLabel.CENTER);
                    label.setVerticalTextPosition(JLabel.BOTTOM);
                    label.setHorizontalTextPosition(JLabel.CENTER);
                    label.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                if (file.isDirectory()) {
                                    // Open directory in the default file manager
                                    panel.reloadViewPanel(file.getAbsolutePath());
                                } else {
                                    // For files, adjust this part according to what you want to do with them
                                    Runtime.getRuntime().exec(new String[]{
                                            "xdg-open",
                                            file.getAbsolutePath()});
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }


                        @Override
                        public void mouseEntered(MouseEvent e) {
                            label.setBackground(Color.BLUE);
                            label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                            currentFile = new File(panel.getCurrentDirectory() + label.getText());
                            repaint();
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            label.setBorder(null);
                            repaint();
                        }
                    });
                    add(label); // Add the label to the panel
                }
            }
        }
    }
}