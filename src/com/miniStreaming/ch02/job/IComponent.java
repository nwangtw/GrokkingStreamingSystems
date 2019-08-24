package com.miniStreaming.ch02.job;

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
}