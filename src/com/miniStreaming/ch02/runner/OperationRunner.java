package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Operation;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class OperationRunner<I, T> extends ComponentRunner<T> {
  private final Operation<I, T> operation;
  private final BlockingQueue<I> incomingStream;

  public OperationRunner(Operation<I, T> operation) {
    super(operation);
    this.operation = operation;
    this.incomingStream = new ArrayBlockingQueue<I>(MAX_QUEUE_SIZE);
  }

  public BlockingQueue<I> getIncomingStream() {
    return incomingStream;
  }

  @Override
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
