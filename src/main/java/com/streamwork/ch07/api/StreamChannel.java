package com.streamwork.ch07.api;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * Example:
 *   Job job = new Job("my_job");
 *   job.addSource(mySource)
 *      .selectChannel("my_channel")
 *      .applyOperator(myOperator);
 */
public class StreamChannel extends Stream {
  private static final long serialVersionUID = -155228644146537952L;
  Stream baseStream;
  String channel;

  public StreamChannel(Stream baseStream, String channel) {
      this.baseStream = baseStream;
      this.channel = channel;
  }

  public Stream applyOperator(Operator operator) {
    return baseStream.applyOperator(channel, operator);
  }
}
