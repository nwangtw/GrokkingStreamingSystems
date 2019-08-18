package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Operator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OperatorRunner<I, T> implements IComponentRunner<I, T> {
  private final int MAX_QUEUE_SIZE = 64;

  private final Operator<I, T> operation;

  private final BlockingQueue<I> incomingStream =
      new ArrayBlockingQueue<I>(MAX_QUEUE_SIZE);
  private final BlockingQueue<T> outgoingStream =
      new ArrayBlockingQueue<T>(MAX_QUEUE_SIZE);

  private final Thread thread;

  public OperatorRunner(Operator<I, T> operation) {
    this.operation = operation;
    this.thread = new Thread() {
      public void run() {
        for (;;) {
          runOnce();
        }
      }
    };
  }

  public BlockingQueue<I> getIncomingStream() {
    return incomingStream;
  }
  public BlockingQueue<T> getOutgoingStream() {  return outgoingStream; }

  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  public boolean runOnce() {
    I event;
    try {
      // Read input
      event = incomingStream.take();
    } catch (InterruptedException e) {
      return false;
    }
    // Apply operation
    T[] outputs = operation.apply(event);
    // Send out
    if (outputs != null) {
      for (T output : outputs) {
        try {
          outgoingStream.put(output);
        } catch (InterruptedException e) {
          return false;  // exit thread
        }
      }
    }
    return true;
  }
}
