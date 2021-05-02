package com.streamwork.ch07.job;

import com.streamwork.ch07.api.Event;
import com.streamwork.ch07.api.FieldsGrouping;

class TransactionFieldsGrouping implements FieldsGrouping {
  private static final long serialVersionUID = 2392789329942799704L;

  public Object getKey(Event event) {
    TransactionScoreEvent e = (TransactionScoreEvent) event;
    return e.transaction.transactionId;
  }
}
