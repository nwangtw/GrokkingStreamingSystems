package com.gss.ch03.api;

import java.io.Serializable;

public interface IGroupingStrategy extends Serializable {
  /**
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  int getKey(Event event);
}
