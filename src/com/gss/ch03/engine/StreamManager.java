package com.gss.ch03.engine;

import com.gss.ch03.api.GroupingStrategy;

import java.util.List;

/**
 * StreamManager is responsible for transporting events from
 * the outgoing event queue of the incoming (upstream) component to
 * the incoming event queue of the (outgoing) downstream component.
 * @param <T> The data type of the events in the queues
 */
public class StreamManager<T> {
  private final ComponentExecutor<?, T> incoming;
  private final List<OperatorExecutor<T, ?>> outgoingList;
  private final Thread thread;

  public StreamManager(ComponentExecutor<?, T> incoming,
                       List<OperatorExecutor<T, ?>> outgoingList) {
    this.incoming = incoming;
    this.outgoingList = outgoingList;
    this.thread = new Thread() {
      public void run() {
        while (true) {
          T event;
          try {
            event = incoming.getOutgoingQueue().take();
          } catch (InterruptedException e) {
            return;
          }

          try {
            for (OperatorExecutor<T, ?> outgoing: outgoingList) {
              GroupingStrategy grouping = outgoing.getGroupingStrategy();
              int key = grouping.getKey(event);
              outgoing.getIncomingQueueByKey(key).put(event);
            }
          } catch (InterruptedException e) {
            return;
          }
        }
      }
    };
  }

  public void start() {
    this.thread.start();
  }
}
