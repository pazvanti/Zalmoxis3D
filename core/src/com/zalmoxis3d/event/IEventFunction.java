package com.zalmoxis3d.event;

import com.zalmoxis3d.event.events.Event;

/**
 * Created by Petre Popescu on 30-Jan-17.
 *
 * This interface is used to provide the structure needed to trigger an event
 * The dispatch() method gets executed if an event gets triggered. The event must be added using addEventListener()
 * method to an EventDispatcher object
 */
public interface IEventFunction<T extends Event>{
    public void dispatch(T event);
}
