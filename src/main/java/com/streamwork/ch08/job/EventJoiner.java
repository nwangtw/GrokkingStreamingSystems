package com.streamwork.ch08.job;

import java.util.HashMap;
import java.util.Map;

import com.streamwork.ch02.job.VehicleCountJob;
import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.JoinOperator;
import com.streamwork.ch08.api.NameEventPair;

class EventJoiner extends JoinOperator {
  private int instance;
  private final Map<Integer, Float> temperatureTable = new HashMap<>();

  public EventJoiner(String name, int parallelism, Map<String, GroupingStrategy> groupingMap) {
    super(name, parallelism, groupingMap);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(String streamName, Event event, EventCollector eventCollector) {
    Logger.log(String.format("EventJoiner: Stream: %s Event: %s\n", streamName, event.toString()));
    if (streamName.equals("temperature")) {
      // Temperature events: materialize into the in memory table.
      TemperatureEvent temperatureEvent = (TemperatureEvent) event;
      temperatureTable.put(temperatureEvent.zone, temperatureEvent.temperature);
    } else {
      // Vehicle events: join with the in memory temperature table and emit.
      VehicleEvent vehicleEvent = (VehicleEvent) event;
      Float temperature = temperatureTable.get(vehicleEvent.zone);
      if (temperature != null) {
        VehicleTemperatureEvent newVehicleEvent = new VehicleTemperatureEvent(vehicleEvent.make, vehicleEvent.model, vehicleEvent.year, vehicleEvent.zone);
        Logger.log(String.format("Found temperature: %f. Patch and emit: %s\n", temperature, newVehicleEvent.toString()));
      } else {
        Logger.log("Temperature not found\n");
      }
    }

  }
}
