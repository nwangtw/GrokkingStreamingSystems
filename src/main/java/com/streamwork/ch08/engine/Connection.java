package com.streamwork.ch08.engine;

/**
 * A util data class for connections between components.
 */
class Connection {
  public final ComponentExecutor from;
  public final OperatorExecutor to;
  public final String channel;
  public final String streamName; // The name of this connection. Used by JoinOperator.

  public Connection(ComponentExecutor from, OperatorExecutor to, String channel, String streamName) {
    this.from = from;
    this.to = to;
    this.channel = channel;
    this.streamName = streamName;
  }
}
