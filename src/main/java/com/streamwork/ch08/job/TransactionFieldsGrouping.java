package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.FieldsGrouping;

class TransactionFieldsGrouping implements FieldsGrouping {
  private static final long serialVersionUID = 2392789329942799704L;

  public Object getKey(Event event) {
    TransactionScoreEvent e = (TransactionScoreEvent) event;
    return e.transaction.transactionId;
  }
}
