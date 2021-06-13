package com.streamwork.ch08.api;

import java.util.List;
import java.util.Map;

/**
 * This is a class used internally. Users should use WindowOperator instead.
 * The class compose a WindowingStrategy and a WindowOperator together into a "regular"
 * Operator object to be used with Stream.
 */
public final class WindowingOperator extends Operator {
  private final Map<String, WindowingStrategy> windowingMap;
  private final WindowOperator operator;

  public WindowingOperator(String name, int parallelism, WindowingStrategy windowing,
      WindowOperator operator, GroupingStrategy grouping) {
    this(name, parallelism, Map.of("default", windowing), operator, Map.of("default", grouping));
  }

  public WindowingOperator(String name, int parallelism, Map<String, WindowingStrategy> windowingMap,
      WindowOperator operator, Map<String, GroupingStrategy> groupingMap) {
    super(name, parallelism, groupingMap);
    this.windowingMap = windowingMap;
    this.operator = operator;
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   */
  public void setupInstance(int instance) {
    operator.setupInstance(instance);
  }

  /**
   * Apply logic to the incoming event. This event based version is already implemented by WindowOperator.
   * Users should implement the windowed version of it.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public final void apply(String streamName, Event event, EventCollector eventCollector) {
    long processingTime = System.currentTimeMillis();
    WindowingStrategy strategy = windowingMap.get(streamName);
    if (event != null) {
      strategy.add(event, processingTime);
    }

    List<EventWindow> windows = strategy.getEventWindows(processingTime);
    for (EventWindow window: windows) {
      operator.apply(streamName, window, eventCollector);
    }
  }
}
