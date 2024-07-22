package com.odits.gui.components;

import javax.swing.*;

public class CustomPopupMenuEmpty extends JPopupMenu {
    public CustomPopupMenuEmpty() {
        this.add("Reload");

        JMenu newMenu = new JMenu("New");
        newMenu.add("File");
        newMenu.add("Folder");
        this.add(newMenu);

    }
}
