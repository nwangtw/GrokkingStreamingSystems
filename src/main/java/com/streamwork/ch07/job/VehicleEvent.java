package com.streamwork.ch07.job;

import com.streamwork.ch07.api.Event;

public class VehicleEvent implements Event {
  private final String type;

  public VehicleEvent(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
