package com.stream_work.ch08.job;

import com.stream_work.ch08.api.Event;

public class TransactionEvent extends Event {
  private final String amount;

  public TransactionEvent(String amount) {
    this.amount = amount;
  }

  @Override
  public String getData() {
    return amount;
  }
}
