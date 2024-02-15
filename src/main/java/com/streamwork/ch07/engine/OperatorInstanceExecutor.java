package com.streamwork.ch07.engine;

import java.util.concurrent.TimeUnit;

import com.streamwork.ch07.api.Event;
import com.streamwork.ch07.api.Operator;
import com.streamwork.ch07.api.WindowingOperator;

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
      // Read input. Time out every one second to check if there is any event windows ready to be processed.
      event = incomingQueue.poll(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operator
    if (operator instanceof WindowingOperator) {
      // WindowingOperator handles null events too.
      operator.apply(event, eventCollector);
    } else if (event != null) {
      // For regular operators.
      operator.apply(event, eventCollector);
    }

    // Emit out
    try {
      for (String channel: eventCollector.getRegisteredChannels()) {
        for (Event output: eventCollector.getEventList(channel)) {
          for (EventQueue queue: outgoingQueueMap.get(channel)) {
            queue.put(output);
          }
        }
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
