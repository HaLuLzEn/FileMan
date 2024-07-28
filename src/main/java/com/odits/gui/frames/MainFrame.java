package com.odits.gui.frames;

import com.formdev.flatlaf.FlatLaf;
import com.odits.gui.panels.MainPanel;
import com.odits.utils.IconLoader;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static com.odits.Main.darkMode;

public class MainFrame extends JFrame implements KeyListener {
    public MainFrame() {
        super("File Mgr");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : lafInfo) {
            System.out.println(info.getClassName());
        }

        ImageIcon windowIcon = IconLoader.loadIcon("/Icons/yoink.png");
        setIconImage(windowIcon.getImage());

        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            } else {
                if (darkMode) {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                } else {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
                }
                
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


        add(new MainPanel(this));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setTheme(boolean darkTheme) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win") && !darkTheme) {
                System.out.println("Windows");
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else if (os.contains("win") && darkTheme) {
                System.out.println("Windows Dark");
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            } else if (os.contains("mac") && !darkTheme) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.mac.MacLookAndFeel");
            } else if (os.contains("mac") && darkTheme) {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            } else if (os.contains("nux") && !darkTheme) {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } else if (os.contains("nux") && darkTheme) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
