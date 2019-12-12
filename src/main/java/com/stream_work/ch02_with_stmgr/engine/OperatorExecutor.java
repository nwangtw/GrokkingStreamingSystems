package com.gss.ch02_with_stmgr.engine;

import com.gss.ch02.api.Event;
import com.gss.ch02.api.Operator;

import java.util.ArrayList;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public class OperatorExecutor extends ComponentExecutor {
  private final Operator operator;

  private final ArrayList<Event> eventCollector = new ArrayList<Event>();

  public OperatorExecutor(Operator operator) { this.operator = operator; }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  @Override
  boolean runOnce() {
    Event event;
    try {
      // Read input
      event = incomingQueue.take();
    } catch (InterruptedException e) {
      return false;
    }

    // Apply operation
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
