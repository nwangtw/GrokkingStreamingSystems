package com.gss.ch02.api;

import java.util.List;

/**
 * This Operator class is the base class for all user defined operators.
 * @param <I> The data type of the events in the incoming stream
 * @param <O> The data type of the events in the outgoing stream
 */
public abstract class Operator<I, O> implements IOperator<I, O> {
  private String name;
  private Stream<O> outgoingStream = new Stream<O>();

  public Operator(String name) {
    this.name = name;
  }

  /**
   * Get the outgoing stream of this component.
   * @return The outgoing stream
   */
  public Stream<O> getOutgoingStream() { return outgoingStream; }

  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  public String getName() { return name; }

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(I event, List<O> eventCollector);
}
