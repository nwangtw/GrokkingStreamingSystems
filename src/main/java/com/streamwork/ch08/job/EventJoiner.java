package com.streamwork.ch08.job;

import java.util.Map;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.JoinOperator;
import com.streamwork.ch08.api.NameEventPair;

class EventJoiner extends JoinOperator {
  private int instance;

  public EventJoiner(String name, int parallelism, Map<String, GroupingStrategy> namedGroupingStrategies) {
    super(name, parallelism, namedGroupingStrategies);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(NameEventPair event, EventCollector eventCollector) {
    Logger.log(String.format("Event pair: %s\n", event.toString()));
  }
}
