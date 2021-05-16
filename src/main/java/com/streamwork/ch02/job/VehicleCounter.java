package com.streamwork.ch02.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Operator;

class VehicleCounter extends Operator {
  private final Map<String, Integer> countMap = new HashMap<String, Integer>();

  public VehicleCounter(String name) {  super(name);  }

  @Override
  public void apply(Event vehicleEvent, List<Event> eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("VehicleCounter --> ");
    printCountMap();
  }

  private void printCountMap() {
    List<String> vehicles = new ArrayList<>(countMap.keySet());
    Collections.sort(vehicles);

    for (String vehicle: vehicles) {
      System.out.println("  " + vehicle + ": " + countMap.get(vehicle));
    }
  }
}
