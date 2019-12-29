package com.gss.ch03.api;

import java.io.Serializable;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class Operator implements IOperator, Serializable {
  private final String name;
  private final int parallelism;
  private final IGroupingStrategy grouping;
  protected final Stream outgoingStream = new Stream();

  public Operator(String name, int parallelism) {
    this.name = name;
    this.parallelism = parallelism;
    this.grouping = new ShuffleGrouping();
  }

  public Operator(String name, int parallelism, IGroupingStrategy grouping) {
    this.name = name;
    this.parallelism = parallelism;
    this.grouping = grouping;
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
   * @return The parallelism (number of instances) of this component
   */
  public int getParallelism() { return parallelism; }

  /**
   * Get the grouping key of an event.
   * @return The grouping strategy of this operator
   */
  public IGroupingStrategy getGroupingStrategy() {
    return grouping;
  }
}
