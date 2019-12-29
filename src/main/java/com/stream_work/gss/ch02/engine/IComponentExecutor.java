package com.gss.ch02.engine;

import com.gss.ch02.api.Event;

import java.util.concurrent.BlockingQueue;

/**
 * This is the interface of all component executor.
 * Each component executor has an incoming event queue and an outgoing event queue.
 */
public interface IComponentExecutor {
  /**
   * Start the executor.
   */
  void start();
  /**
   * Get the outgoing event queue.
   */
  BlockingQueue<Event> getOutgoingQueue();
}
