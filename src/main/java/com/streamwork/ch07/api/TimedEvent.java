package com.streamwork.ch07.api;

/**
 * This is the interface for all the event classes.
 * Users should implement this interface to manage their data.
 */
public interface TimedEvent extends Event {
    /**
     * Get event time (since midnight, January 1, 1970 UTC) in milliseconds.
     * @return
     */
    long getTime();
}
