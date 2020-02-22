package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;
import com.stream_work.ch08.api.EventCollector;
import com.stream_work.ch08.api.GroupingStrategy;
import com.stream_work.ch08.api.Operator;

class TransactionLocationAnalyzer extends Operator {
  private static final long serialVersionUID = 6898974811832135305L;

  private int instance = 0;

  public TransactionLocationAnalyzer(String name, int parallelism) {
    super(name, parallelism);
  }

  public TransactionLocationAnalyzer(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event event, EventCollector eventCollector) {
    Logger.log("proximity analyzer (" + getName() + ") :: instance " + instance + " -->\n" + event.getData() + "\n");
    TransactionEvent transactionEvent = (TransactionEvent)event;
    if (!transactionEvent.getUserLocation().equals(transactionEvent.getTransactionLocation())) {
      // transaction occurred in a different physical location.  This could be fraudulent
      transactionEvent.addToFraudScore();
    }

    eventCollector.add("default", event);
  }


}
