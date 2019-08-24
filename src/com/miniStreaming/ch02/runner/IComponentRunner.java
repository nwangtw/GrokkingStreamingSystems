package com.miniStreaming.ch02.runner;

import java.util.concurrent.BlockingQueue;

/**
 * This is the interface of all component runners.
 * Each component runner has an incoming event queue and an outgoing event queue.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public interface IComponentRunner<I, O> {
  /**
   * Start the runner.
   */
  void start();
}
