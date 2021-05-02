package com.streamwork.ch07.api;

/**
 * This is the interface for all the windowing strategies.
 */
public interface WindowingStrategy {
    /**
     * Add an event into the windowing strategy. If an event window is ready to be processed, the event window
     * object is returned;
     * @param event
     * @return an event window if it is ready to be processed.
     */
    EventWindow add(Event event);
}
