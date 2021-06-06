package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;

public class EmissionEvent implements Event {
  public final int zone;
  public final double emission;

  public EmissionEvent(int zone, double emission) {
    this.zone = zone;
    this.emission = emission;
  }
}
