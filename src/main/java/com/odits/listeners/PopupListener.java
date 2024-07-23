package com.odits.listeners;

import com.odits.gui.panels.MainPanel;
import com.odits.gui.panels.ViewPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PopupListener implements ActionListener {
    private MainPanel mainPanel;
    private ViewPanel viewPanel;
    private String filePath;

    public PopupListener(ViewPanel viewPanel, MainPanel mainPanel) {
        this.viewPanel = viewPanel;
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Open":
                filePath = viewPanel.getSelectedIconPath();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (!file.isDirectory()) {
                        ViewPanel.executeFile(filePath);
                    } else {
                        mainPanel.reloadViewPanel(filePath);
                        mainPanel.reloadTree(file.getAbsoluteFile());
                        if (mainPanel.getTreeScrollPane().isVisible()) {
                            mainPanel.getSplitPane().setDividerLocation(200);
                        }
                        mainPanel.repaint();
                        mainPanel.revalidate();
                    }
                }
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
