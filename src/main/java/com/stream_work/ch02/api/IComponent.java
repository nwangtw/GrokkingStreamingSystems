package com.gss.ch02.api;

/**
 * The interface for all components, including Source and Operator.
 * Every component has an incoming stream and an outgoing stream.
 */
public interface IComponent {
  /**
   * Get the outgoing stream of the component.
   * @return The outgoing stream
   */
  Stream getOutgoingStream();
  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  String getName();
}