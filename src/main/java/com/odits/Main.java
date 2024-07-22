package com.odits;

import com.odits.listeners.GlobalKeyListener;
import com.odits.gui.frames.MainFrame;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);

    }
}
