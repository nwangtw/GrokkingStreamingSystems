package com.streamwork.ch08.api;

/**
 * The NamedStream class represents a data stream with a name.
 * It is used by JoinOperator so that the operator knows the type of events.
 * Example:
 *   NamedStreams.of(Map.of(stream1, "stream1", stream2, "stream2"))
 *      .applyOperator(myJoinOperator);
 */
/*
public class NamedStream {
  Stream stream;
  String name;

  public StreamChannel(Stream stream, String name) {
      this.stream = stream;
      this.name = name;
  }

  public Stream applyOperator(JoinOperator operator) {
    return baseStream.applyOperator(channel, operator);
  }
}
*/
