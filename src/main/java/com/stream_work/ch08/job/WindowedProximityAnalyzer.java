package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.GroupingStrategy;
import com.stream_work.ch08.api.Operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WindowedProximityAnalyzer extends Operator {
  private static final long serialVersionUID = 6898974811832135305L;

  private int instance = 0;

  public WindowedProximityAnalyzer(String name, int parallelism) {
    super(name, parallelism);
  }

  public WindowedProximityAnalyzer(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event event, EventCollector eventCollector) {
    //String vehicle = ((TransactionEvent)transactionEvent).getData();
   // Integer count = countMap.getOrDefault(vehicle, 0) + 1;
    //countMap.put(vehicle, count);

    Logger.log("apply (" + getName() + ") :: instance " + instance + " -->\n" + event.getData() + "\n");
  }


}
