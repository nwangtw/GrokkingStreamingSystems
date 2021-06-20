package com.streamwork.ch08.engine;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.GroupingStrategy;
import com.streamwork.ch08.api.NameEventPair;

/**
 * EventDispatcher is responsible for transporting events from
 * the incoming queue to the outgoing queues with a grouping strategy.
 */
public class EventDispatcher extends Process {
  private final OperatorExecutor downstreamExecutor;
  private EventQueue incomingQueue = null;
  private NamedEventQueue [] outgoingQueues = null;

  public EventDispatcher(OperatorExecutor downstreamExecutor) {
    this.downstreamExecutor = downstreamExecutor;
  }

  @Override
  boolean runOnce() {
    try {
      Event event = incomingQueue.take();

      final GroupingStrategy grouping = downstreamExecutor.getGroupingStrategy(incomingQueue.streamName);
      int instance = grouping.getInstance(event, outgoingQueues.length);
      if (instance == GroupingStrategy.ALL_INSTANCES) {
        for (NamedEventQueue queue: outgoingQueues) {
          queue.put(new NameEventPair(incomingQueue.streamName, event));
        }
      } else {
        outgoingQueues[instance].put(new NameEventPair(incomingQueue.streamName, event));
      }
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }

  public void setIncomingQueue(EventQueue queue) {
    incomingQueue = queue;
  }

  public void setOutgoingQueues(NamedEventQueue [] queues) {
    outgoingQueues = queues;
  }
}
