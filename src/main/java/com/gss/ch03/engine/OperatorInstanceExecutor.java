package com.gss.ch03.engine;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;
import com.gss.ch03.api.Operator;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public class OperatorInstanceExecutor extends InstanceExecutor {
  private final int instanceId;
  private final Operator operator;
  private final BlockingQueue<Event> incomingEvents;
  private final BlockingQueue<Event> outgoingEvents;

  private final ArrayList<Event> eventCollector = new ArrayList<Event>();

  public OperatorInstanceExecutor(int instanceId,
                                  Operator operator,
                                  BlockingQueue<Event> incomingEvents,
                                  BlockingQueue<Event> outgoingEvents) {
    this.instanceId = instanceId;
    this.operator = operator;
    this.incomingEvents = incomingEvents;
    this.outgoingEvents = outgoingEvents;
  }

  public BlockingQueue<Event> getIncomingQueue() {
    return incomingEvents;
  }

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

    // Apply operator
    operator.apply(instanceId, event, eventCollector);

    // Emit out
    try {
      for (Event output : eventCollector) {
        outgoingEvents.put(output);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }

  public IGroupingStrategy getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
