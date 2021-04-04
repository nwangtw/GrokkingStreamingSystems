package com.streamwork.ch02.api;

import java.util.List;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class Source extends Component {
  public Source(String name) {
    super(name);
  }

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  public abstract void getEvents(List<Event> eventCollector);
}
