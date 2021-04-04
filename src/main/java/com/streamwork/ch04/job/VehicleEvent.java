package com.stream_work.ch04.job;

import com.stream_work.ch04.api.Event;

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
