package com.zalmoxis3d.event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by petre.popescu on 2017-03-20.
 */
public class EventHandler {
    private Map<String, Set<EventDispatcher>> itemsWithEvents;

    private static EventHandler INSTANCE = new EventHandler();

    private EventHandler() {
        itemsWithEvents = new HashMap<String, Set<EventDispatcher>>();
    }

    public static EventHandler getInstance() {
        return INSTANCE;
    }

    public void addEventDispatcher(String eventType, EventDispatcher item) {
        Set<EventDispatcher> itemsOfSameEventType = itemsWithEvents.get(eventType);
        if (itemsOfSameEventType == null) {
            itemsOfSameEventType = new HashSet<EventDispatcher>();
        }
        itemsOfSameEventType.add(item);
        itemsWithEvents.put(eventType, itemsOfSameEventType);
    }

    public void removeEventDispatcher(String eventType, EventDispatcher item) {
        Set<EventDispatcher> itemsOfSameEventType = itemsWithEvents.get(eventType);
        if (itemsOfSameEventType == null) return;

        itemsOfSameEventType.remove(item);
        itemsWithEvents.put(eventType, itemsOfSameEventType);
    }

    public Set<EventDispatcher> getItemsTriggered(String eventType) {
        return itemsWithEvents.get(eventType);
    }
}
