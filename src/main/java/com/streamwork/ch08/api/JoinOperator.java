package com.streamwork.ch08.api;

import java.util.Map;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class JoinOperator extends Operator {
  public JoinOperator(String name, int parallelism) {
    super(name, parallelism);
  }

  public JoinOperator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  /**
   * Apply materialize logic to the incoming events. The results should be stored internally to be referenced
   * by the apply() function.
   * @param streamName
   * @param event
   */
  public abstract void materialize(String streamName, Event event);
}
