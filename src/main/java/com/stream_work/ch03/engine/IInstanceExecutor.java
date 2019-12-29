package com.gss.ch03.engine;

/**
 * This is the interface of all component executor.
 * Each component executor has an incoming event queue and an outgoing event queue.
 */
public interface IInstanceExecutor {
  /**
   * Start the executor.
   */
  void start();
}
