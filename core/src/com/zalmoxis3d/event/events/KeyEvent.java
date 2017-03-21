package com.zalmoxis3d.event.events;

/**
 * Created by petre.popescu on 2017-03-21.
 */
public class KeyEvent extends Event {
    public static final String KEY_DOWN = "keyDown";
    public static final String KEY_UP = "keyUp";

    private int keyCode;

    public KeyEvent(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return this.keyCode;
    }
}
