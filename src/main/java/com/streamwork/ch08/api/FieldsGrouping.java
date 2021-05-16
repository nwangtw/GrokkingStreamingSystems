package com.streamwork.ch08.api;

import java.io.Serializable;

public interface FieldsGrouping extends GroupingStrategy, Serializable {
  /**
   * Get key from an event. Child class can override this function
   * to calculate key in different ways. For example, calculate the
   * key from some specific fields.
   * @param event The event object to extract key from.
   * @return The extracted key.
   */
  Object getKey(Event event);

  /**
   * Get target instance id from an event and component parallelism.
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  @Override
  public default int getInstance(Event event, int parallelism) {
    return Math.abs(getKey(event).hashCode()) % parallelism;
  }
}
