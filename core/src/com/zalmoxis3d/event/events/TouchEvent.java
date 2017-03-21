package com.zalmoxis3d.event.events;

/**
 * Created by petre.popescu on 2017-03-20.
 *
 * A Touch Event that stores the touch event details
 */
public class TouchEvent extends Event {
    public static final String TOUCH_UP = "touchUp";
    public static final String TOUCH_DOWN = "touchDown";
    public static final String MOUSE_MOVED = "mouseMove";

    private int mouseButton;
    public TouchEvent(int mouseButton, String type) {
        super(type);
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return this.mouseButton;
    }
}
