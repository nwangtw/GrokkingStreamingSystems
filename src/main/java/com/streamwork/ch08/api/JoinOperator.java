package com.streamwork.ch08.api;

import java.util.Map;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class JoinOperator extends Operator {
  private final Map<String, GroupingStrategy> namedGroupingStrategies;
  private static final GroupingStrategy DEFAULT_GROUPING = new AllGrouping();

  public JoinOperator(String name, int parallelism, Map<String, GroupingStrategy> namedGroupingStrategies) {
    super(name, parallelism);
    this.namedGroupingStrategies = namedGroupingStrategies;
  }

  /**
   * Apply logic to the incoming event. This version is not suitable for JoinOperator.
   * Users should implement the special version of it.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public final void apply(Event event, EventCollector eventCollector) {
    if (event instanceof NameEventPair) {
      apply((NameEventPair) event, eventCollector);
    } else {
      throw new RuntimeException("apply(Event, EventCollector) is not supported by JoinOperator.");
    }
  }

  /**
   * Get the grouping key of an event by stream name. Fallback to the default grouping strategy
   * if not configured.
   * @return The grouping strategy of this operator
   */
  public GroupingStrategy getGroupingStrategyByStreamName(String streamName) {
    return namedGroupingStrategies.getOrDefault(streamName, DEFAULT_GROUPING);
  }

  /**
   * Apply logic to the incoming event.
   * @param nameEventPair The incoming stream name/event pairs.
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(NameEventPair nameEventPair, EventCollector eventCollector);
}
