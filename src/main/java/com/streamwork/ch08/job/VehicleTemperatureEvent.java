package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;

public class VehicleTemperatureEvent implements Event {
  public final String make;
  public final String model;
  public final int year;
  public final int zone;
  public final float temperature;
  public final long time;

  public VehicleTemperatureEvent(String make, String model, int year, int zone, float temperature, long time) {
    this.make = make;
    this.model = model;
    this.year = year;
    this.zone = zone;
    this.temperature = temperature;
    this.time = time;
  }

  @Override
  public String toString() {
    return String.format("[make:%s; model:%s, year:%d, zone: %d, temperature:%f, time:%d]",
        make, model, year, zone, temperature, time);
  }
}
