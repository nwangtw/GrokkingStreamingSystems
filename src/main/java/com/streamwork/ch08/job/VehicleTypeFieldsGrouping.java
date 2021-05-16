package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.FieldsGrouping;

class VehicleTypeFieldsGrouping implements FieldsGrouping {
  private static final long serialVersionUID = -1432651705385964857L;

  public Object getKey(Event event) {
    VehicleEvent e = (VehicleEvent) event;
    return e.getType();
  }
}
