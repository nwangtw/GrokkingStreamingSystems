package com.gss.ch02.job;

import com.gss.ch02.api.Event;
import com.gss.ch02.api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TollBooth extends Operator {
  private final Map<String, Integer> countMap = new HashMap<String, Integer>();

  public TollBooth(String name) {  super(name);  }

  @Override
  public void apply(Event vehicleEvent, List<Event> eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("vehicle: " + vehicle + ", count: " + count);
  }
}
