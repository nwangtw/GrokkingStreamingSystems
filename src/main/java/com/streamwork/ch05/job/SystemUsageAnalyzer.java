package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Event;
import com.streamwork.ch05.api.EventCollector;
import com.streamwork.ch05.api.GroupingStrategy;
import com.streamwork.ch05.api.Operator;

class SystemUsageAnalyzer extends Operator {
  private int instance;
  private int transactionCount = 0;
  private int fraudTransactionCount = 0;

  public SystemUsageAnalyzer(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  @Override
  public void setupInstance(int instance) {
    this.instance = instance;
  }

  @Override
  public void apply(Event event, EventCollector collector) {
    transactionCount++;

    // TODO: uncomment the code below to count fraud transactions.
    // TransactionEvent e = ((TransactionEvent)event);
    // String id = ((TransactionEvent)event).transactionId;
    // Thread.sleep(20);
    // boolean fraud = fraudStore.getItem(id);
    // if (fraud) {
    //        fraudTransactionCount++;
    //}
    collector.add(new UsageEvent( transactionCount, fraudTransactionCount));
  }
}
