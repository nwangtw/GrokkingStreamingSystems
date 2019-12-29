package com.stream_work.ch02.job;

import com.stream_work.ch02.api.Event;

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
