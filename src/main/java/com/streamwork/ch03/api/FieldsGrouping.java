package com.streamwork.ch03.api;

import java.io.Serializable;

public class FieldsGrouping implements GroupingStrategy, Serializable {
  private static final long serialVersionUID = -1121182295793347601L;

  public FieldsGrouping() {
  }

  /**
   * Get key from an event. Child class can override this function
   * to calculate key in different ways. For example, calculate the
   * key from some specific fields.
   * @param event The event object to extract key from.
   * @return The data to be hashed.
   */
  protected Object getKey(Event event) {
    return event.getData();
  }

  /**
   * Get target instance id from an event and component parallelism.
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  @Override
  public int getInstance(Event event, int parallelism) {
    return Math.abs(getKey(event).hashCode()) % parallelism;
  }
}
