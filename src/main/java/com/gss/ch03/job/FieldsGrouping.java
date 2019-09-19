package com.gss.ch03.job;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;

public class FieldsGrouping implements IGroupingStrategy {
  public FieldsGrouping() { }

  /**
   * Get key from an event.
   * @param vehicleEvent The event object to extract key from.
   * @return The integer key of this event.
   */
  @Override
  public int getKey(Event vehicleEvent) {
    String vehicle = ((VehicleEvent)vehicleEvent).getData();
    return vehicle.hashCode();
  }
}
