package com.odits.gui.components;

import javax.swing.*;

public class CustomMenuItem extends JMenuItem {
    private boolean open;

    public CustomMenuItem(String text, boolean open) {
        super(text);
        this.open = open;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
