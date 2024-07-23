package com.odits.gui.components;

import javax.swing.*;

public class IconLabel extends JLabel {
    private boolean isSelected = false;
    private String path;

    public IconLabel() {
        super();
    }

    public IconLabel(String name, ImageIcon icon, int pos, String path) {
        super(name, icon, pos);
        this.path = path;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
