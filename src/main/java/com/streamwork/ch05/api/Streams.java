package com.streamwork.ch05.api;

import java.util.ArrayList;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * Example:
 *  Streams.merge(stream1, stream2, stream3).applyOperator(operator)
 * or
 *   Streams.merge(stream1.selectChannel("second_channel"), stream2, stream3).applyOperator(operator)
 */
public class Streams {
  ArrayList<Stream> streams;

  private Streams(ArrayList<Stream> streams) {
    this.streams = streams;
  }

  public static Streams of(Stream ...streams) {
    ArrayList<Stream> list = new ArrayList<Stream>();
    for (Stream stream: streams) {
        list.add(stream);
    }
    return new Streams(list);
  }

  /**
   * Apply the operator to the streams.
   * @param operator
   * @return
   */
  public Stream applyOperator(Operator operator) {
    for (Stream stream: streams) {
      stream.applyOperator(operator);
    }
    return operator.getOutgoingStream();
  }
}
