package com.loukag.Listener;

import java.awt.event.KeyAdapter;

/**
 * KeyboardListener class
 * Listens for keyboard input
 */
public class KeyboardListener extends KeyAdapter {

        /**
         * Array of booleans that represent whether a key is pressed or not
         */
        public static boolean[] keys = new boolean[256];

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            if(e.getKeyCode() < 256)
                keys[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            if(e.getKeyCode() < 256)
                keys[e.getKeyCode()] = false;
        }

        public static boolean isKeyPressed(int keyCode) {
            return keys[keyCode];
        }
}
