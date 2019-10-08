package com.gss.ch03.api;

/**
 * With shuffle grouping, the events are routed to downstream
 * instances relatively evenly. This implementation is round robin
 * based instead of random number based because it is simpler and
 * deterministic.
 */
public class ShuffleGrouping implements IGroupingStrategy {
  private int count = 0;

  public ShuffleGrouping() { }

  /**
   * Get target instance id from an event and component parallelism.
   * @param event The event object to route to the component.
   * @param The parallelism of the component.
   * @return The integer key of this event.
   */
  @Override
  public int getInstance(Event event, int parallelism) {
    if (count >= parallelism) {
      count = 0;
    }
    count++;
    return count - 1;
  }
}
