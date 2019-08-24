package com.miniStreaming.ch02.job;

/**
 * This Source class is the base class for all user defined sources.
 * @param <O> The data type of the events in the outgoing stream
 */
public abstract class Source<O> implements ISource<O> {
  protected Stream<O> outgoingStream = new Stream();

  /**
   * Get the outgoing stream of the component.
   * @return The outgoing stream
   */
  public Stream<O> getOutgoingStream() { return outgoingStream; }

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @return The events emitted by the source
   */
  public abstract O[] getEvents();
}
