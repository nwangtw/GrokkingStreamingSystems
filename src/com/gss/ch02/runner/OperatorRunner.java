package com.gss.ch02.runner;

import com.gss.ch02.job.Operator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The runner for operator components. When the runner is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public class OperatorRunner<I, O> extends ComponentRunner<I, O> {
  private final int MAX_INCOMNG_QUEUE_SIZE = 64;
  private final BlockingQueue<I> incomingStream =
      new ArrayBlockingQueue<I>(MAX_INCOMNG_QUEUE_SIZE);

  private final Operator<I, O> operation;

  public OperatorRunner(Operator<I, O> operation) {
    this.operation = operation;
  }

  public BlockingQueue<I> getIncomingQueue() {
    return incomingStream;
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    I event;
    try {
      // Read input
      event = incomingStream.take();
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operation
    O[] outputs = operation.apply(event);

    // Emit out
    if (outputs != null) {
      for (O output : outputs) {
        try {
          getOutgoingQueue().put(output);
        } catch (InterruptedException e) {
          return false;  // exit thread
        }
      }
    }
    return true;
  }
}
