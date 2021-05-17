package com.streamwork.ch07.api;

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
  WindowingStrategy strategy;

  public WindowedStream(Stream baseStream, WindowingStrategy strategy) {
      this.baseStream = baseStream;
      this.strategy = strategy;
  }

  public Stream applyOperator(WindowOperator operator) {
    return baseStream.applyWindowOperator(strategy, operator);
  }
}
