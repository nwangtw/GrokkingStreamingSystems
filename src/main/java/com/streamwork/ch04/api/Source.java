package com.streamwork.ch04.api;

import java.io.Serializable;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class Source extends Component implements Serializable {
  private static final long serialVersionUID = 5661182901439251287L;

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
  public abstract void getEvents(EventCollector eventCollector);
}
