package com.gss.ch03.engine;

import com.gss.ch03.api.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public class OperatorInstanceExecutor<I, O> extends InstanceExecutor<I, O> {
  private final int instanceId;
  private final Operator<I, O> operator;

  private final int MAX_INCOMNG_QUEUE_SIZE = 64;
  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<I> incomingEvents =
      new ArrayBlockingQueue<I>(MAX_INCOMNG_QUEUE_SIZE);
  private final BlockingQueue<O> outgoingeEvents =
      new ArrayBlockingQueue<O>(MAX_OUTGOING_QUEUE_SIZE);

  public OperatorInstanceExecutor(int instanceId, Operator<I, O> operator) {
    this.instanceId = instanceId;
    this.operator = operator;
  }

  public BlockingQueue<I> getIncomingQueue() {
    return incomingEvents;
  }
  public BlockingQueue<O> getOutgoingQueue() { return outgoingeEvents; }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    I event;
    try {
      // Read input
      event = incomingEvents.take();
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operator
    List<O> collector = new ArrayList<>();
    operator.apply(event, collector);

    // Emit out
    try {
      for (O output : collector) {
        getOutgoingQueue().put(output);
      }
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
