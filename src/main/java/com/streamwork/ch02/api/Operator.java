package com.streamwork.ch02.api;

import java.util.List;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class Operator extends Component {
  public Operator(String name) {
    super(name);
  }

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(Event event, List<Event> eventCollector);
}
