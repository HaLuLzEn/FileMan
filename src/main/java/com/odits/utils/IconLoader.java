package com.odits.utils;

import javax.swing.*;

public class IconLoader {

    public static ImageIcon loadIcon(String path) {
        java.net.URL imgURL = IconLoader.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
