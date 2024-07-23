package com.odits.listeners;

import com.odits.gui.panels.ViewPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupListener implements ActionListener {
    private ViewPanel viewPanel;
    private String filePath;
    public PopupListener(ViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Open":
                filePath = viewPanel.getSelectedIconPath();
                ViewPanel.executeFile(filePath);
                break;
            case "":

                break;
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
