package com.streamwork.ch07.api;

import java.io.Serializable;

/**
 * The base class for all components, including Source and Operator.
 */
public class Component implements Serializable {
  private static final long serialVersionUID = 1291075149377521635L;

  private String name;
  private int parallelism;
  // The stream object is used to connect the downstream operators.
  private Stream outgoingStream = new Stream();

  public Component(String name, int parallelism) {
    this.name = name;
    this.parallelism = parallelism;
  }

  /**
   * Get the name of this component.
   * @return The name of this component.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the parallelism (number of instances) of this component.
   * @return The parallelism (number of instances) of this component.
   */
  public int getParallelism() {
    return parallelism;
  }

  /**
   * Get the outgoing event stream of this component. The stream is used to connect
   * the downstream components.
   * @return
   */
  public Stream getOutgoingStream() {
    return outgoingStream;
  }
}
