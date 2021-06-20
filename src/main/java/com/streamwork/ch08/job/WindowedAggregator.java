package com.streamwork.ch08.job;

import java.util.HashMap;
import java.util.Map;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.EventWindow;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.WindowOperator;

class WindowedAggregator extends WindowOperator {
  private int instance;

  public WindowedAggregator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(String streamName, EventWindow window, EventCollector eventCollector) {
    Map<Integer, Double> emissions = new HashMap<>();
    for (Event event: window.getEvents()) {
      EmissionEvent emissionEvent = (EmissionEvent) event;
      int zone = emissionEvent.zone;
      if (emissions.containsKey(zone)) {
        emissions.put(zone, emissions.get(zone) + emissionEvent.emission);
      } else {
        emissions.put(zone, emissionEvent.emission);
      }
    }
    for (Map.Entry<Integer, Double> entry: emissions.entrySet()) {
      Logger.log(
          String.format("WindowedAggregator :: instance %d --> total emission for zone %d  between %d and %d is %f\n",
              instance, entry.getKey(), window.getStartTime(), window.getEndTime(), entry.getValue()
          )
      );
    }
  }
}
