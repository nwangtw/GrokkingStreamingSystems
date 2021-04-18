package com.streamwork.ch05.job;

import com.streamwork.ch05.api.Event;
import com.streamwork.ch05.api.FieldsGrouping;

class UserAccountFieldsGrouping implements FieldsGrouping {
  private static final long serialVersionUID = -921436160232556027L;

  public Object getKey(Event event) {
    TransactionEvent e = (TransactionEvent) event;
    return e.userAccount;
  }
}
