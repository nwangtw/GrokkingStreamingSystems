package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;

/**
 * A simple transaction event used in the fraud detection job.
 */
public class TemperatureEvent implements Event {
  public final int zone;
  public final float temperature;

  public TemperatureEvent(int zone, float temperature) {
    this.zone = zone;
    this.temperature = temperature;
  }

  @Override
  public String toString() {
    return String.format("[zone:%d; temperature:%f]", zone, temperature);
  }
}
