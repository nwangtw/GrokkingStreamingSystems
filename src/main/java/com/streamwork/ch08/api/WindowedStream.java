package com.streamwork.ch08.api;

import java.util.Map;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * Example:
 *   Job job = new Job("my_job");
 *   job.addSource(mySource)
 *      .withWindowing(new FixedTimeWindowingStrategy(1000, 1000))
 *      .applyOperator(myOperator);
 */
public class WindowedStream extends Stream {
  Stream baseStream;
  Map<String, WindowingStrategy> windowingMap;

  public WindowedStream(Stream baseStream, Map<String, WindowingStrategy> windowingMap) {
      this.baseStream = baseStream;
      this.windowingMap = windowingMap;
  }

  public Stream applyOperator(WindowOperator operator) {
    return baseStream.applyWindowOperator(windowingMap, operator);
  }

  // Joins are special type of operations. Side streams are needed for join operators
  // and materialize() function is applied to these side streams.
  public Stream join(Operator operator, Map<String, Stream> streams) {
    return null;
  }

  public Stream windowedJoin(Operator operator, Map<String, WindowedStream> streams) {
    return null;
  }

}
