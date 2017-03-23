package com.zalmoxis3d.event.events;

import com.zalmoxis3d.event.EventDispatcher;

/**
 * Created by petre.popescu on 2017-03-21.
 *
 * A Key Event that stores the key event details
 */
public class KeyEvent extends Event {
    public static final String KEY_DOWN = "keyDown";
    public static final String KEY_UP = "keyUp";

    private int keyCode;

    public KeyEvent(int keyCode, String type) {
        super(type);
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return this.keyCode;
    }
}
