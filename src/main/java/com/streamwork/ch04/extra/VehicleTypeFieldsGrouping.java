package com.streamwork.ch04.extra;

import com.streamwork.ch04.api.Event;
import com.streamwork.ch04.api.FieldsGrouping;

class VehicleTypeFieldsGrouping implements FieldsGrouping {
  private static final long serialVersionUID = -1432651705385964857L;

  public Object getKey(Event event) {
    VehicleEvent e = (VehicleEvent) event;
    return e.getType();
  }
}
