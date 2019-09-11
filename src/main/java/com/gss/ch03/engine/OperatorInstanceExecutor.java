package com.gss.ch03.engine;

import com.gss.ch03.api.GroupingStrategy;
import com.gss.ch03.api.Operator;

import java.util.ArrayList;
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
  private final BlockingQueue<I> incomingEvents;
  private final BlockingQueue<O> outgoingEvents;

  private final ArrayList<O> eventCollector = new ArrayList<O>();

  public OperatorInstanceExecutor(int instanceId,
                                  Operator<I, O> operator,
                                  BlockingQueue<I> incomingEvents,
                                  BlockingQueue<O> outgoingEvents) {
    this.instanceId = instanceId;
    this.operator = operator;
    this.incomingEvents = incomingEvents;
    this.outgoingEvents = outgoingEvents;
  }

  public BlockingQueue<I> getIncomingQueue() {
    return incomingEvents;
  }

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
    operator.apply(event, eventCollector);

    // Emit out
    try {
      for (O output : eventCollector) {
        outgoingEvents.put(output);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }

  public GroupingStrategy<I> getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
