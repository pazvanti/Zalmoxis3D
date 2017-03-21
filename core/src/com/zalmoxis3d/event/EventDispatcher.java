package com.zalmoxis3d.event;

import com.zalmoxis3d.event.events.Event;

import java.util.*;

/**
 * Created by Petre Popescu on 30-Jan-17.
 */
public class EventDispatcher {
    private Map<String, List<IEventFunction>> eventsMap;

    public EventDispatcher() {
        eventsMap = new HashMap<String, List<IEventFunction>>();
    }

    public void addEventListener(String type, IEventFunction event) {
        if (event == null) return;
        List<IEventFunction> eventsOfSameType = eventsMap.get(type);
        if (eventsOfSameType == null) {
            eventsOfSameType = new ArrayList<IEventFunction>();
        }
        if (eventsOfSameType.contains(event)) return;
        eventsOfSameType.add(event);

        eventsMap.put(type, eventsOfSameType);
        EventHandler.getInstance().addEventDispatcher(type, this);
    }

    public boolean hasEventListener(String type) {
        return eventsMap.get(type) != null;
    }

    public void removeEventListener(String type, IEventFunction event) {
        List<IEventFunction> eventsOfSameType = eventsMap.get(type);
        if (eventsOfSameType == null) return;
        eventsOfSameType.remove(event);

        if (eventsOfSameType.isEmpty()) {
            eventsMap.put(type, null);
            EventHandler.getInstance().removeEventDispatcher(type, this);
        }
    }
    public  void removeEventListeners() {
        Iterator<Map.Entry<String, List<IEventFunction>>> iterator = this.eventsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            EventHandler.getInstance().removeEventDispatcher(iterator.next().getKey(), this);
        }
        this.eventsMap = new HashMap<String, List<IEventFunction>>();
    }

    public void dispatchEvent(IEventFunction eventFunction, Event event) {
        if (event == null) return;
        eventFunction.dispatch(event);
    }

    public void dispatchEvents(String type, Event event) {
        List<IEventFunction> eventsOfSameType = eventsMap.get(type);
        if (eventsOfSameType == null) return;

        for(IEventFunction eventFunction:eventsOfSameType) {
            eventFunction.dispatch(event);
        }
    }
}
