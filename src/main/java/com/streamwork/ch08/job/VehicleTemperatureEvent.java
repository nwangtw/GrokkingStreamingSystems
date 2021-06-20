package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;

public class VehicleTemperatureEvent implements Event {
  public final String make;
  public final String model;
  public final int year;
  public final float temperature;

  public VehicleTemperatureEvent(String make, String model, int year, float temperature) {
    this.make = make;
    this.model = model;
    this.year = year;
    this.temperature = temperature;
  }

  @Override
  public String toString() {
    return String.format("[make:%s; model:%s, year:%d, temperature:%d]", make, model, year, temperature);
  }
}
