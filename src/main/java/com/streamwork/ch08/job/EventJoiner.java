package com.streamwork.ch08.job;

import java.util.Map;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.EventCollector;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.JoinOperator;
import com.streamwork.ch08.api.NameEventPair;

class EventJoiner extends JoinOperator {
  private int instance;

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
      // Temperature events. Materialize into the in memory table.
    } else {
      // Vehicle events. Join with the in memory table.
    }

  }
}
