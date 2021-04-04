package com.streamwork.ch04.engine;

/**
 * A util data class for connections between components.
 */
class Connection {
  public final ComponentExecutor from;
  public final OperatorExecutor to;
  public final String channel;

  public Connection(ComponentExecutor from, OperatorExecutor to, String channel) {
    this.from = from;
    this.to = to;
    this.channel = channel;
  }
}
