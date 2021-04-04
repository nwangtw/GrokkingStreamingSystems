package com.streamwork.ch03.api;

import java.io.Serializable;
import java.util.List;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class Source extends Component implements Serializable {
  private static final long serialVersionUID = -4760189183877654639L;

  public Source(String name, int parallelism) {
    super(name, parallelism);
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   */
  public abstract void setupInstance(int instance);

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  public abstract void getEvents(List<Event> eventCollector);
}
