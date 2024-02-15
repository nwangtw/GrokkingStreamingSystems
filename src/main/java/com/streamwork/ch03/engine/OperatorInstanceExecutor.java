package com.streamwork.ch03.engine;

import com.streamwork.ch03.api.Event;
import com.streamwork.ch03.api.Operator;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public class OperatorInstanceExecutor extends InstanceExecutor {
  private final int instanceId;
  private final Operator operator;

  public OperatorInstanceExecutor(int instanceId, Operator operator) {
    this.instanceId = instanceId;
    this.operator = operator;
    operator.setupInstance(instanceId);
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exit.
   */
  protected boolean runOnce() {
    Event event;
    try {
      // Read input
      event = incomingQueue.take();
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operator
    operator.apply(event, eventCollector);

    // Emit out
    try {
      for (Event output : eventCollector) {
        outgoingQueue.put(output);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
