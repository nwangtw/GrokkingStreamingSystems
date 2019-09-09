package com.gss.ch03.api;

public abstract class GroupingStrategy<T> {
  public GroupingStrategy() { }

  /**
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  public abstract int getKey(T event);
}
