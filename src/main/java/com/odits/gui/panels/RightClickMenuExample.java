package com.odits.gui.panels;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RightClickMenuExample extends JPanel {
    public RightClickMenuExample() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemEdit = new JMenuItem("Edit");
        JMenuItem menuItemDelete = new JMenuItem("Delete");
        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemDelete);

        // Add action listeners for menu items (optional)
        menuItemEdit.addActionListener(e -> System.out.println("Edit selected"));
        menuItemDelete.addActionListener(e -> System.out.println("Delete selected"));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RightClickMenuExample());
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
