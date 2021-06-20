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
    VehicleTemperatureEvent vehicleTemp = (VehicleTemperatureEvent) event;
    double emission = emissionTable.getEmission(
      vehicleTemp.make, vehicleTemp.model, vehicleTemp.year, vehicleTemp.temperature, 4);
    EmissionEvent emissionEvent = new EmissionEvent(vehicleTemp.time, vehicleTemp.zone, emission);
    eventCollector.add(emissionEvent);
    Logger.log(
        String.format(
            "EmissionResolver :: instance %d --> resolved emission %s\n",
            instance, emissionEvent.toString()
        )
    );
  }
}
