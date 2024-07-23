package com.odits.gui.components;

import com.odits.gui.panels.MainPanel;
import com.odits.gui.panels.ViewPanel;
import com.odits.listeners.PopupListener;

import javax.swing.*;

public class CustomPopupMenu extends JPopupMenu {
    public CustomPopupMenu(ViewPanel viewPanel, MainPanel mainPanel) {
        PopupListener popupListener = new PopupListener(viewPanel, mainPanel);

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
