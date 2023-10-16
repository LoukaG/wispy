package com.loukag.Listener;

import java.awt.event.KeyAdapter;

public class KeyboardListener extends KeyAdapter {

        public static boolean[] keys = new boolean[256];

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            keys[e.getKeyCode()] = true;
        }

        @Override
        public void keyReleased(java.awt.event.KeyEvent e) {
            keys[e.getKeyCode()] = false;
        }

        public static boolean isKeyPressed(int keyCode) {
            return keys[keyCode];
        }
}
