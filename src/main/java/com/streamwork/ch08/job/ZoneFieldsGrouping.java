package com.streamwork.ch08.job;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.FieldsGrouping;

class ZoneFieldsGrouping implements FieldsGrouping {
  public Object getKey(Event event) {
    EmissionEvent e = (EmissionEvent) event;
    return e.zone;
  }
}
