package com.odits.listeners;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class GlobalKeyListener {
    public static void initGlobalKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.isControlDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_W:
                                System.exit(0);
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
