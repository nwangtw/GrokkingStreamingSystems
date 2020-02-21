package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.Operator;

public class TransactionAmountAnalyzer extends Operator {

  private static final long serialVersionUID = -1946540737692143313L;

  private int instance = 0;

  public TransactionAmountAnalyzer(String name, int parallelism) {
    super(name, parallelism);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event event, EventCollector eventCollector) {
    Logger.log("average ticket analyzer (" + getName() + ") :: instance " + instance + " -->\n" + event.getData() + "\n");
    TransactionEvent transactionEvent = (TransactionEvent) event;
    if (transactionEvent.getAmount() % 2 == 0) {
      ((TransactionEvent)event).addToFraudScore();
    }
    eventCollector.add("default", event);
  }
}
