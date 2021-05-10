package com.streamwork.ch07.api;

/**
 * This WindowedOperator class is the base class for all user defined operators
 * that support windowed computations.
 */
public abstract class WindowedOperator extends Operator {
  private final WindowingStrategy windowing;

  public WindowedOperator(String name, int parallelism, WindowingStrategy windowing) {
    super(name, parallelism);
    this.windowing = windowing;
  }

  public WindowedOperator(String name, int parallelism, WindowingStrategy windowing, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
    this.windowing = windowing;
  }

  public WindowingStrategy getWindowingStrategy() {
    return windowing;
  }

  /**
   * Apply logic to the incoming event. This event based version is already implemented by WindowedOperator.
   * Users should implement the windowed version of it.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public final void apply(Event event, EventCollector eventCollector) {
    throw new RuntimeException("apply(Event, EventCollector) is not supported by WindowedOperator.");
  }

  public abstract void apply(EventWindow window, EventCollector eventCollector);
}
