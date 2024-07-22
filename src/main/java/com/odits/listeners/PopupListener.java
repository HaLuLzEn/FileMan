package com.odits.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopupListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Open":
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    System.out.println("Windows shi");
                //    Runtime.getRuntime().exec("cmd /c start \"\" \"" + filePath + "\"");
                } else {
                    System.out.println("Linux shi");
                    /*
                    Runtime.getRuntime().exec(new String[]{
                            "xdg-open", filePath
                    });
                     */
                }
                break;
        }
    }
}
