package com.gss.ch02.job;

import com.gss.ch02.api.Event;

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
