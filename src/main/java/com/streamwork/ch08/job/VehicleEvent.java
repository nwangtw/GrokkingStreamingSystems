package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;

public class VehicleEvent implements Event {
  public final String make;
  public final String model;
  public final int year;
  public final int zone;
  public final long time;

  public VehicleEvent(String make, String model, int year, int zone, long time) {
    this.make = make;
    this.model = model;
    this.year = year;
    this.zone = zone;
    this.time = time;
  }

  @Override
  public String toString() {
    return String.format("[make:%s; model:%s, year:%d, zone:%d, time:%d]", make, model, year, zone, time);
  }
}
