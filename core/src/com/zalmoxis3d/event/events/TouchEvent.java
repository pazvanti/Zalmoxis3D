package com.zalmoxis3d.event.events;

import com.badlogic.gdx.math.Vector2;
import com.zalmoxis3d.event.EventDispatcher;

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
    private Vector2 touchPoint;
    public TouchEvent(int mouseButton, int screenX, int screenY, String type) {
        super(type);
        this.mouseButton = mouseButton;
        this.touchPoint = new Vector2(screenX, screenY);
    }

    public int getMouseButton() {
        return this.mouseButton;
    }

    public Vector2 getTouchPoint() {
        return this.touchPoint;
    }
}
