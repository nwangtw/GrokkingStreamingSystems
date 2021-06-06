package com.streamwork.ch08.job;

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
  public void apply(EventWindow window, EventCollector eventCollector) {
    Logger.log(String.format("%d transactions are received between %d and %d\n",
        window.getEvents().size(), window.getStartTime(), window.getEndTime()));
    for (Event event: window.getEvents()) {
      Logger.log(String.format("Event: %s\n", event));
    }
  }
}
