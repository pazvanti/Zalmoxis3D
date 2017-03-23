package com.zalmoxis3d.event.events;

import com.zalmoxis3d.event.EventDispatcher;

/**
 * Created by petre.popescu on 2017-03-20.
 *
 * The basic event class that stores event details
 */
public class Event {
    public static final String ENTER_FRAME = "enterFrame";
    private String type;
    private EventDispatcher currentTarget;

    public Event(String type) {
        this.type = type;
    }

    public void setCurrentTarget(EventDispatcher currentTarget) {
        this.currentTarget = currentTarget;
    }

    public EventDispatcher getCurrentTarget() {
        return this.currentTarget;
    }

    public String getType() {
        return this.type;
    }
}
