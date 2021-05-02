package com.streamwork.ch07.api;

import java.util.List;

/**
 * This is the interface for all the event classes.
 * Users should implement this interface to manage their data.
 */
public class EventWindow {
  public List<Event> getEvents() {
    return null;
  }

  // Get the start timestamp of the window. The time is *inclusive*.
  public long getStartTime() {
    return 0;
  }

  // Get the end timestamp of the window. The time is *exclusive*.
  public long getEndTime() {
      return 0;
  }
}
