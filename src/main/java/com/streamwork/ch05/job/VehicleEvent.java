package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Event;

public class VehicleEvent extends Event {
  private final String type;

  public VehicleEvent(String type) {
    this.type = type;
  }

  @Override
  public String getData() {
    return type;
  }
}
