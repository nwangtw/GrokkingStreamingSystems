package com.streamwork.ch04.job;

import com.streamwork.ch04.api.Event;
import com.streamwork.ch04.api.EventCollector;
import com.streamwork.ch04.api.GroupingStrategy;
import com.streamwork.ch04.api.Operator;

class ScoreAggregator extends Operator {
  private static final long serialVersionUID = -224012821029619376L;
  private int instance;

  public ScoreAggregator(String name, int parallelism, GroupingStrategy grouping) {
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
