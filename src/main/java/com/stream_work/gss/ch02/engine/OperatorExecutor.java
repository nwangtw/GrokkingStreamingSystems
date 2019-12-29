package com.gss.ch02.engine;

import com.gss.ch02.api.Event;
import com.gss.ch02.api.Operator;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public class OperatorExecutor extends ComponentExecutor {
  private final Operator operator;

  private final int MAX_INCOMNG_QUEUE_SIZE = 64;
  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<Event> incomingEvents =
      new ArrayBlockingQueue<Event>(MAX_INCOMNG_QUEUE_SIZE);
  private final BlockingQueue<Event> outgoingEvents =
      new ArrayBlockingQueue<Event>(MAX_OUTGOING_QUEUE_SIZE);
  private final ArrayList<Event> eventCollector = new ArrayList<Event>();

  public OperatorExecutor(Operator operator) { this.operator = operator; }

  public BlockingQueue<Event> getIncomingQueue() {
    return incomingEvents;
  }
  public BlockingQueue<Event> getOutgoingQueue() { return outgoingEvents; }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    Event event;
    try {
      // Read input
      event = incomingEvents.take();
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operation
    operator.apply(event, eventCollector);

    // Emit out
    try {
      for (Event output : eventCollector) {
        getOutgoingQueue().put(output);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
