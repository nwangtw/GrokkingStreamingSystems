package com.streamwork.ch03.api;

/**
 * This is the base class for all the event classes.
 * Users should extend this class to implement all their own event classes.
 */
public abstract class Event {
  /**
   * Get data stored in the event.
   * @return The data stored in the event
   */
  public abstract Object getData();
}
