package com.gss.ch02.api;

import java.util.List;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class Source implements ISource {
  private String name;
  private Stream outgoingStream = new Stream();

  public Source(String name) {
    this.name = name;
  }

  /**
   * Get the outgoing stream of this component.
   * @return The outgoing stream
   */
  public Stream getOutgoingStream() { return outgoingStream; }

  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  public String getName() { return name; }

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  public abstract void getEvents(List<Event> eventCollector);
}
