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
                    switch (e.getKeyCode()) {
                        default:
                            System.out.println(e.getKeyCode());
                            break;
                    }
                }
                // Return false to allow the key event to be dispatched to the focused component
                return false;
            }
        });
    }
}
