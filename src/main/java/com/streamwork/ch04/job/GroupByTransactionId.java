package com.streamwork.ch04.job;

import com.streamwork.ch04.api.Event;
import com.streamwork.ch04.api.FieldsGrouping;

class GroupByTransactionId implements FieldsGrouping {
  private static final long serialVersionUID = 2392789329942799704L;

  public Object getKey(Event event) {
    TransactionScoreEvent e = (TransactionScoreEvent) event;
    return e.transaction.transactionId;
  }
}
