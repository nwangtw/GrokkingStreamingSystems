package com.stream_work.ch04.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stream_work.ch04.api.Event;
import com.stream_work.ch04.api.EventCollector;
import com.stream_work.ch04.api.IGroupingStrategy;
import com.stream_work.ch04.api.Operator;

class TollBooth extends Operator {
  private static final long serialVersionUID = 6898974811832135305L;

  private Map<String, Integer> countMap = new HashMap<String, Integer>();
  private int instance = 0;

  public TollBooth(String name, int parallelism) {
    super(name, parallelism);
  }

  public TollBooth(String name, int parallelism, IGroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event vehicleEvent, EventCollector eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("toll booth (" + getName() + ") :: instance " + instance + " --> ");
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
