package com.streamwork.ch08.api;

/**
 * This WindowedOperator class is the base class for all user defined operators
 * that support windowed computations.
 */
public abstract class WindowOperator extends Operator {
  public WindowOperator(String name, int parallelism) {
    super(name, parallelism);
  }

  public WindowOperator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  /**
   * Apply logic to the incoming event. This event based version is already implemented by WindowedOperator.
   * Users should implement the windowed version of it.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public final void apply(String streamName, Event event, EventCollector eventCollector) {
    throw new RuntimeException("apply(Event, EventCollector) is not supported by WindowedOperator.");
  }

  public abstract void apply(String streamName, EventWindow window, EventCollector eventCollector);
}
