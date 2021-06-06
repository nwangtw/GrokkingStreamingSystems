package com.streamwork.ch08.api;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class NamedStreams {
  private final Map<Stream, String> namedStreams;

  private NamedStreams(Map<Stream, String> namedStreams) {
    this.namedStreams = namedStreams;
  }

  public static NamedStreams of(Map<Stream, String> namedStreams) {
    return new NamedStreams(namedStreams);
  }

  /**
   * Apply the operator to the streams. This object is used by Join operator only.
   * @param operator
   * @return
   */
  public Stream join(JoinOperator operator) {
    for (Map.Entry<Stream, String> namedStream: namedStreams.entrySet()) {
      String streamName = namedStream.getValue();
      GroupingStrategy grouping = operator.getGroupingStrategyByStreamName(streamName);
      namedStream.getKey().applyOperator(operator, streamName);
    }
    return operator.getOutgoingStream();
  }
}
