package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Event;

/**
 * A simple transaction event used in the fraud detection job.
 */
public class UsageEvent implements Event {
  public final int transactionCount;
  public final int fraudTransactionCount;

  public UsageEvent(int transactionCount, int fraudTransactionCount) {
    this.transactionCount = transactionCount;
    this.fraudTransactionCount = fraudTransactionCount;
  }

  @Override
  public String toString() {
    return String.format("[transaction count: %d; fraud transaction count: %d]",
        transactionCount, fraudTransactionCount);
  }
}
