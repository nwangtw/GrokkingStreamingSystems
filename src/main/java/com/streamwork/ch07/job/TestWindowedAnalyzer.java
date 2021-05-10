package com.streamwork.ch07.job;

import com.streamwork.ch07.api.EventCollector;
import com.streamwork.ch07.api.EventWindow;
import com.streamwork.ch07.api.GroupingStrategy;
import com.streamwork.ch07.api.WindowedOperator;
import com.streamwork.ch07.api.WindowingStrategy;

class TestWindowedAnalyzer extends WindowedOperator {
  private int instance;

  public TestWindowedAnalyzer(String name, int parallelism, WindowingStrategy windowing, GroupingStrategy grouping) {
    super(name, parallelism, windowing, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(EventWindow window, EventCollector eventCollector) {
    Logger.log(String.format("%d transactions are received between %d and %d",
        window.getEvents().size(), window.getStartTime(), window.getEndTime()));
  }
}
