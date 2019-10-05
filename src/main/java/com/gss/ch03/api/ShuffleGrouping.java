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
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  @Override
  public int getKey(Event event) {
    int r = count;
    if (count < Integer.MAX_VALUE) {
      count++;
    } else {
      count = 0;
    }
    return r;
  }
}
