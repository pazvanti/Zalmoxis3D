package com.zalmoxis3d.event.events;

/**
 * Created by petre.popescu on 2017-03-20.
 *
 * The basic event class that stores event details
 */
public class Event {
    private String type;
    public Event(String type) {
    }

    public String getType() {
        return this.type;
    }
}