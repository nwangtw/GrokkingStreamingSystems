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

  public WindowedOperator(String name, int parallelism, GroupingStrategy grouping, WindowingStrategy windowing) {
    super(name, parallelism, grouping);
    this.windowing = windowing;
  }

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public final void apply(Event event, EventCollector eventCollector) {
    EventWindow window = windowing.add(event);
    if (window != null) {
      apply(window, eventCollector);
    }
  }

  public abstract void apply(EventWindow window, EventCollector eventCollector);
}
