package com.streamwork.ch08.engine;

import java.util.Map;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.GroupingStrategy;

/**
 * MultipleIncomingQueueEventDispatcher is a special type of EventDispatcher. It is
 * responsible for transporting events from multiple named incoming queue to
 * one outgoing queues to a JoiningOperator.
 * The events in the outgoing queue are NameEventPairs instead of Events.
 */
/*
public class MultipleIncomingQueueEventDispatcher extends EventDispatcher {
  private final OperatorExecutor downstreamExecutor;
  private Map<EventQueue, String> incomingQueues = null;
  private EventQueue [] outgoingQueues = null;

  public MultipleIncomingQueueEventDispatcher(OperatorExecutor downstreamExecutor) {
    this.downstreamExecutor = downstreamExecutor;
  }

  @Override
  boolean runOnce() {
    try {
      Event event = incomingQueue.take();

      GroupingStrategy grouping = downstreamExecutor.getGroupingStrategy();
      int instance = grouping.getInstance(event, outgoingQueues.length);
      if (instance == GroupingStrategy.ALL_INSTANCES) {
        for (EventQueue queue: outgoingQueues) {
          queue.put(event);
        }
      } else {
        outgoingQueues[instance].put(event);
      }
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }

  public void setIncomingQueues(Map<EventQueue, String> queues) {
    incomingQueues = queues;
  }

  public void setOutgoingQueues(EventQueue [] queues) {
    outgoingQueues = queues;
  }
}
*/
