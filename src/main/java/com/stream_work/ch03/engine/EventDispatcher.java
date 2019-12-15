package com.stream_work.ch03.engine;

import java.util.concurrent.BlockingQueue;

import com.stream_work.ch03.api.Event;
import com.stream_work.ch03.api.IGroupingStrategy;

/**
 * EventDispatcher is responsible for transporting events from
 * the incoming queue to the outgoing queues with a grouping strategy.
 */
public class EventDispatcher extends Process {
  private final OperatorExecutor downstreamExecutor;
  private BlockingQueue<Event> incomingQueue = null;
  private BlockingQueue<Event> [] outgoingQueues = null;

  public EventDispatcher(OperatorExecutor downstreamExecutor) {
    this.downstreamExecutor = downstreamExecutor;
  }

  @Override
  boolean runOnce() {
    try {
      Event event = incomingQueue.take();

      IGroupingStrategy grouping = downstreamExecutor.getGroupingStrategy();
      int instance = grouping.getInstance(event, outgoingQueues.length);
      outgoingQueues[instance].put(event);
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }

  public void setIncomingQueue(BlockingQueue<Event> queue) {
    incomingQueue = queue;
  }

  public void setOutgoingQueues(BlockingQueue<Event> [] queues) {
    outgoingQueues = queues;
  }
}
