package com.gss.ch03.api;

import java.io.Serializable;
import java.util.List;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class Source implements ISource, Serializable {
  private String name;
  private int parallelism;
  protected Stream outgoingStream = new Stream();

  public Source(String name, int parallelism) {
    this.name = name;
    this.parallelism = parallelism;
  }

  /**
   * Get the outgoing stream of the component.
   * @return The outgoing stream
   */
  public Stream getOutgoingStream() { return outgoingStream; }

  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  public String getName() { return name; }

  /**
   * Get the parallelism (number of instances) of this component.
   * @return The parallelism (number of instances) of this component.
   */
  public int getParallelism() { return parallelism; }

  /**
   * Set up this source object.
   */
  public void setup() { }

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  public abstract void getEvents(List<Event> eventCollector);
}
