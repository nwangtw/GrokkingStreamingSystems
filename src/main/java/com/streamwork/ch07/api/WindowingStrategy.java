package com.streamwork.ch07.api;

import java.io.Serializable;
import java.util.List;

/**
 * This is the interface for all the windowing strategies.
 */
public interface WindowingStrategy extends Serializable {
    /**
     * Add an event into the windowing strategy. Note that all calculation in this function are event time
     * based, except the logic to check the event is a late event or not.
     * @param event
     */
    void add(Event event, long processingTime);

    /**
     * Get the event windows that are ready to be processed. It is based on the current processing time.
     * @return the list of event windows to be processed.
     */
    List<EventWindow> getEventWindows(long processingTime);
}
