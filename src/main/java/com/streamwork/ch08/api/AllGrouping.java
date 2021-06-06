package com.streamwork.ch08.api;

import java.io.Serializable;

/**
 * With all grouping, each event is routed to all downstream
 * instances.
 */
public class AllGrouping implements GroupingStrategy, Serializable {

  public AllGrouping() { }

  /**
   * Get target instance id from an event and component parallelism.
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  @Override
  public int getInstance(Event event, int parallelism) {
    return ALL_INSTANCES;
  }
}
