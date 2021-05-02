package com.streamwork.ch07.api;

/**
 * This is a time based fixed windowing strategy. It supports
 */
public class FixedTimeWindowingStrategy implements WindowingStrategy {
    /**
     * Add an event into the windowing strategy. If an event window is ready to be processed, the event window
     * object is returned;
     * @param event
     * @return an event window if it is ready to be processed.
     */
    @Override
    public EventWindow add(Event event) {
        return null;
    }
}
