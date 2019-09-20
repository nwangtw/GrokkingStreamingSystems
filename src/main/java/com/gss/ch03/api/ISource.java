package com.gss.ch03.api;

import java.util.List;

/**
 * The interface for the sources.
 * A source accepts events from the outside world and emits into the system
 * via the outgoing stream,
 */
public interface ISource extends IComponent {
    /**
     * Set up instance.
     * @param instance The instance id (an index starting from 0) of this source instance.
     */
    void setupInstance(int instance);

    /**
     * Accept events from external into the system.
     * The function is abstract and needs to be implemented by users.
     * @param eventCollector The outgoing event collector
     */
    void getEvents(List<Event> eventCollector);
}
