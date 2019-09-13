package com.gss.ch03.api;

/**
 * The interface for the operators.
 * An operation reads events from the incoming stream, apply logic on them,
 * and emits result to the outgoing stream.
 */
public interface IOperator extends IComponent {
  /**
   * Set up this operator object.
   */
  void setup();
}
