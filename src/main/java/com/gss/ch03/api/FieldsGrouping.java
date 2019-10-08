package com.gss.ch03.api;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;

public class FieldsGrouping implements IGroupingStrategy {
  public FieldsGrouping() { }

  /**
   * Get key from an event. Child class can override this function
   * to calculate key in different ways. For example, calculate the
   * key from some specific fields.
   * @param event The event object to extract key from.
   * @return The hash of this event as the key.
   */
  protected int getKey(Event event) {
    Object data = event.getData();
    return data.hashCode();
  }

  /**
   * Get target instance id from an event and component parallelism.
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  @Override
  public int getInstance(Event event, int parallelism) {
    return getKey(event) % parallelism;
  }
}
