package com.odits.listeners;

import com.odits.gui.panels.MainPanel;

import javax.swing.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class GlobalKeyListener {
    public static void initGlobalKeyListener(MainPanel mainPanel) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.isControlDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_W:
                                System.exit(0);
                                break;
                            case KeyEvent.VK_O:
                                System.out.println("Open");
                                JFileChooser fileChooser = new JFileChooser();
                                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                String dir = fileChooser.showDialog(mainPanel, "Open Directory") == JFileChooser.APPROVE_OPTION ? fileChooser.getSelectedFile().getAbsolutePath() : null;
                                mainPanel.reloadViewPanel(dir);
                                break;
                            default:
                                System.out.println(e.getKeyCode());
                                break;
                        }
                    }
                }
                return false;
            }
        });
    }
}
