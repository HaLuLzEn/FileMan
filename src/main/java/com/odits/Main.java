package com.odits;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;

import com.odits.gui.frames.MainFrame;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static boolean darkMode = false;

    public static void main(String[] args) {
        darkMode = isDarkTheme();
        System.out.println("Dark mode: " + darkMode);
        SwingUtilities.invokeLater(MainFrame::new);
    }

    public static boolean isDarkTheme() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return isWindowsDarkTheme();
        } else if (os.contains("mac")) {
            return isMacDarkTheme();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return isLinuxDarkTheme();
        }
        return false;
    }

    private static boolean isWindowsDarkTheme() {
        try {
            ProcessBuilder builder = new ProcessBuilder(
                    "reg", "query", "HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", "/v", "AppsUseLightTheme");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("0x0")) {
                    return true; // Dark theme
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Light theme
    }

    private static boolean isMacDarkTheme() {
        try {
            ProcessBuilder builder = new ProcessBuilder("defaults", "read", "-g", "AppleInterfaceStyle");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            return "Dark".equalsIgnoreCase(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Light theme
    }

    private static boolean isLinuxDarkTheme() {
        try {
            ProcessBuilder builder = new ProcessBuilder("gsettings", "get", "org.gnome.desktop.interface", "gtk-theme");
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine().toLowerCase();
            return (line.contains("dark") || line.contains("black") || line.contains("dracula"));
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Default to light theme
        }
    }
}
