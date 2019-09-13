package com.gss.ch03.api;

import java.io.Serializable;

public abstract class GroupingStrategy implements Serializable {
  public GroupingStrategy() { }

  /**
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  public abstract int getKey(Event event);
}
