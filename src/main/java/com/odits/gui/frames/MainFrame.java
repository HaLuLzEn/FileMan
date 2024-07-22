package com.odits.gui.frames;

import com.odits.gui.panels.MainPanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener {
    public MainFrame() {
        super("File Mgr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : lafInfo) {
            System.out.println(info.getClassName());
        }


        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            } else {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        add(new MainPanel());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
