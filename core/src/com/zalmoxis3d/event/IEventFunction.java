package com.zalmoxis3d.event;

import com.zalmoxis3d.event.events.Event;

/**
 * Created by Petre Popescu on 30-Jan-17.
 */
public interface IEventFunction {
    public void dispatch(Event event);
}
