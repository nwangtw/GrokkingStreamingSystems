package com.streamwork.ch08.job;

import com.streamwork.ch08.api.TimedEvent;

public class EmissionEvent implements TimedEvent {
  public final long time;
  public final int zone;
  public final double emission;

  public EmissionEvent(long time, int zone, double emission) {
    this.time = time;
    this.zone = zone;
    this.emission = emission;
  }

  public long getTime() {
    return time;
  }

  @Override
  public String toString() {
    return String.format("[time:%d; zone:%d, emission:%f]", time, zone, emission);
  }
}
