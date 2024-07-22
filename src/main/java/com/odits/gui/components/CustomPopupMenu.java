package com.odits.gui.components;

import com.odits.listeners.PopupListener;

import javax.swing.*;

public class CustomPopupMenu extends JPopupMenu {

    public CustomPopupMenu() {
        PopupListener popupListener = new PopupListener();

        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(popupListener);
        this.add(openItem);
        this.add("Copy");

        JMenu moveMenu = new JMenu("Move to");
        this.add(moveMenu);

        this.addSeparator();

        this.add("Rename");
        this.add("Delete");
    }
}
