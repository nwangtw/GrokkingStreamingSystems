package com.streamwork.ch08.api;

import java.util.Map;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class Operator extends Component {
  // Grouping strategy for the incoming data.
  private final Map<String, GroupingStrategy> groupingMap;

  public Operator(String name, int parallelism) {
    this(name, parallelism, new ShuffleGrouping());
  }

  public Operator(String name, int parallelism, GroupingStrategy grouping) {
    this(name, parallelism, Map.of("default", grouping));
  }

  public Operator(String name, int parallelism, Map<String, GroupingStrategy> groupingMap) {
    super(name, parallelism);
    this.groupingMap = groupingMap;
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   */
  public abstract void setupInstance(int instance);

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * @param streamName The stream name of the incoming event. All events are from the same stream
   *   and the value can be ignored for most operators. It is important for join operators.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(String streamName, Event event, EventCollector eventCollector);

  /**
   * Get the grouping strategy for a specific stream.
   * @return The grouping strategy of this operator
   */
  public GroupingStrategy getGroupingStrategy(String streamName) {
    return groupingMap.get(streamName);
  }

/**
   * Get the grouping strategy map. This function is used by WindowingOperator only.
   * @return The grouping strategy of this operator
   */
  Map<String, GroupingStrategy> getGroupingStrategyMap() {
    return groupingMap;
  }
}
