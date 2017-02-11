package com.zalmoxis3d.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        eventsMap.put(type, eventsOfSameType);
    }

    public boolean hasEventListener(String type) {
        return eventsMap.get(type) != null;
    }

    public void removeEventListener(String type, IEventFunction event) {
        List<IEventFunction> eventsOfSameType = eventsMap.get(type);
        if (eventsOfSameType == null) return;
        eventsOfSameType.remove(event);

        if (eventsOfSameType.isEmpty()) eventsMap.put(type, null);
    }
    public  void removeEventListeners() {
        this.eventsMap = new HashMap<String, List<IEventFunction>>();
    }

    public void dispatchEvent(IEventFunction event) {
        if (event == null) return;
        event.dispatch();
    }

    public void dispatchEvents(String type) {
        List<IEventFunction> eventsOfSameType = eventsMap.get(type);
        if (eventsOfSameType == null) return;

        for(IEventFunction event:eventsOfSameType) {
            event.dispatch();
        }
    }
}
