package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.Event;
import com.streamwork.ch04.api.EventCollector;
import com.streamwork.ch04.api.GroupingStrategy;
import com.streamwork.ch04.api.Operator;

import java.util.*;

class TollBooth extends Operator {
  private static final long serialVersionUID = 6898974811832135305L;

  private Map<String, Integer> countMap = new HashMap<String, Integer>();
  private int instance = 0;

  public TollBooth(String name, int parallelism) {
    super(name, parallelism);
  }

  public TollBooth(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event vehicleEvent, EventCollector eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getType();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    String countMap = printCountMap();
    Logger.log("toll booth (" + getName() + ") :: instance " + instance + " -->\n" + countMap);
  }

  private String printCountMap() {
    StringBuilder builder = new StringBuilder();
    List<String> vehicles = new ArrayList<>(countMap.keySet());
    Collections.sort(vehicles);

    for (String vehicle: vehicles) {
      builder.append("  " + vehicle + ": " + countMap.get(vehicle) +"\n");
    }
    return builder.toString();
  }
}
