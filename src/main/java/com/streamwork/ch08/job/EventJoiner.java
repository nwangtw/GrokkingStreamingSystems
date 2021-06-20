package com.streamwork.ch08.job;

import java.util.HashMap;
import java.util.Map;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.JoinOperator;

class EventJoiner extends JoinOperator {
  private final static float DEFAULT_TEMPERATURE = 60;
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
    Logger.log(
        String.format(
            "EventJoiner :: instance %d --> Stream: %s Event: %s\n",
            instance, streamName, event.toString()
        )
    );
    if (streamName.equals("temperature")) {
      // Temperature events: materialize into the in memory table. No event is emitted.
      TemperatureEvent temperatureEvent = (TemperatureEvent) event;
      temperatureTable.put(temperatureEvent.zone, temperatureEvent.temperature);
      Logger.log(
          String.format(
              "EventJoiner :: instance %d --> update temperature table: %d -> %f.\n",
            instance, temperatureEvent.zone, temperatureEvent.temperature
          )
      );
    } else {
      // Vehicle events: join with the in memory temperature table and emit.
      VehicleEvent vehicleEvent = (VehicleEvent) event;
      Float temperature = temperatureTable.get(vehicleEvent.zone);
      VehicleTemperatureEvent newVehicleEvent;
      if (temperature != null) {
        newVehicleEvent = new VehicleTemperatureEvent(vehicleEvent.make, vehicleEvent.model, vehicleEvent.year, vehicleEvent.zone, temperature, vehicleEvent.time);
        Logger.log(
            String.format(
                "EventJoiner :: instance %d --> found temperature: %f. Patch and emit: %s\n",
              instance, temperature, newVehicleEvent.toString()
            )
        );
      } else {
        newVehicleEvent = new VehicleTemperatureEvent(vehicleEvent.make, vehicleEvent.model, vehicleEvent.year, vehicleEvent.zone, DEFAULT_TEMPERATURE, vehicleEvent.time);
        Logger.log(
            String.format(
                "EventJoiner :: instance %d --> temperature not found. Default to %f and emit %s.\n",
                instance, DEFAULT_TEMPERATURE, newVehicleEvent.toString()
            )
        );
      }
      eventCollector.add(newVehicleEvent);
    }
  }
}
