package com.gss.ch03.api;

import java.util.Random;

public class ShuffleGrouping implements IGroupingStrategy {
  private final Random rand = new Random();

  public ShuffleGrouping() { }

  /**
   * Get key from an event.
   * @param event The event object to extract key from.
   * @return The integer key of this event.
   */
  @Override
  public int getKey(Event event) {
    return Math.abs(rand.nextInt());
  }
}
