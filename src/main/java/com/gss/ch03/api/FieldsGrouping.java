package com.gss.ch03.api;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;

public class FieldsGrouping implements IGroupingStrategy {
  public FieldsGrouping() { }

  /**
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  @Override
  public int getKey(Event event) {
    Object data = event.getData();
    return data.hashCode();
  }
}
