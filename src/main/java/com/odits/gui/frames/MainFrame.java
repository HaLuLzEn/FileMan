package com.odits.gui.frames;

import com.odits.gui.panels.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("File Mgr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new MainPanel());
        setVisible(true);
    }
}
