package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.Operator;

class EmissionResolver extends Operator {
  private int instance;
  private final EmissionTable emissionTable = new EmissionTable();

  public EmissionResolver(String name, int parallelism) {
    super(name, parallelism);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(String streamName, Event event, EventCollector eventCollector) {
    VehicleEvent vehicleEvent = (VehicleEvent) event;
    double emission = emissionTable.getEmission(
        vehicleEvent.make, vehicleEvent.model, vehicleEvent.year, 4);
    eventCollector.add(new EmissionEvent(vehicleEvent.zone, emission) );
  }
}
