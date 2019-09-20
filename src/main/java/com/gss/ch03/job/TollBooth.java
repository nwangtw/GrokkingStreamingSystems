package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;
import com.gss.ch03.api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TollBooth extends Operator {
  private Map<String, Integer> countMap = new HashMap<String, Integer>();
  private int instance = 0;

  public TollBooth(String name, int parallelism, IGroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event vehicleEvent, List<Event> eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("toll booth :: instance " + instance + " --> ");
    printCountMap();
  }

  private void printCountMap() {
    for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
      System.out.println("  " + entry.getKey() + ": " + entry.getValue());
    }
  }
}
