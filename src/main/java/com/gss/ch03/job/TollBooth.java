package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;
import com.gss.ch03.api.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TollBooth extends Operator {
  private Map<String, Integer> countMap = new HashMap<String, Integer>();

  public TollBooth(String name, int parallelism, IGroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void apply(int instance, Event vehicleEvent, List<Event> eventCollector) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    countMap.put(vehicle, count);

    System.out.println("toll booth instance; " + instance +
                       ", vehicle: " + vehicle +
                       ", count: " + count);
  }
}
