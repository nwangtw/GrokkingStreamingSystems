package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Event;
import com.streamwork.ch05.api.EventCollector;
import com.streamwork.ch05.api.GroupingStrategy;
import com.streamwork.ch05.api.Operator;

class UsageWriter extends Operator {
  private static final long serialVersionUID = -224012821029619376L;
  private int instance;

  public UsageWriter(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event score, EventCollector eventCollector) {
    TransactionScoreEvent e = ((TransactionScoreEvent)score);
  }
}
