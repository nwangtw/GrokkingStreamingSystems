package com.streamwork.ch07.job;

import java.time.Instant;

import com.streamwork.ch07.api.TimedEvent;

/**
 * A simple transaction event used in the fraud detection job.
 */
public class TransactionEvent implements TimedEvent {
  public final String transactionId;
  public final float amount;
  public final Instant transactionTime;
  public final long merchandiseId;
  public final long userAccount;

  public TransactionEvent(String transactionId, float amount, Instant transactionTime,
      long merchandiseId, long userAccount) {
    this.transactionId = transactionId;
    this.amount = amount;
    this.transactionTime = transactionTime;
    this.merchandiseId = merchandiseId;
    this.userAccount = userAccount;
  }

  @Override
  public String toString() {
    return String.format("[transaction:%s; amount:%f; transactionTime: %s; merchandise: %d, user: %d]",
        transactionId, amount, transactionTime, merchandiseId, userAccount);
  }

  @Override
  public long getTime() {
    return transactionTime.toEpochMilli();
  }
}
