package com.gss.ch03.api;

/**
 * The interface for all components, including Source and Operator.
 * Every component has an incoming stream and an outgoing stream.
 * @param <O> The data type of the events in the outgoing stream
 */
public interface IComponent<O> {
  /**
   * Get the outgoing stream of the component.
   * @return The outgoing stream
   */
  Stream<O> getOutgoingStream();
  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  String getName();

  /**
   * Get the parallelism (number of instances) of this component.
   * @return The parallelism (number of instances) of this component.
   */
  int getParallelism();
}