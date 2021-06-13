package com.streamwork.ch08.api;

import java.util.Map;

/**
 * This Operator class is the base class for all user defined operators that accept events from different
 * types of streams.
 */
public abstract class JoinOperator extends Operator {
  private static final GroupingStrategy DEFAULT_GROUPING = new AllGrouping();

  public JoinOperator(String name, int parallelism, Map<String, GroupingStrategy> groupingMap) {
    super(name, parallelism, groupingMap);
  }

  /**
   * Get the grouping key of an event by stream name. Fallback to the default grouping strategy
   * if not configured.
   * @return The grouping strategy of this operator
   */
  @Override
  public GroupingStrategy getGroupingStrategy(String streamName) {
    GroupingStrategy grouping = super.getGroupingStrategy(streamName);
    return grouping != null ? grouping : DEFAULT_GROUPING;
  }
}
