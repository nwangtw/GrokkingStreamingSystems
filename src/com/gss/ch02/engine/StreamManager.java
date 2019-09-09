package com.gss.ch02.engine;

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
          for (OperatorExecutor<T, ?> outgoing: outgoingList) {
            try {
              outgoing.getIncomingQueue().put(event);
            } catch (InterruptedException e) {
              return;
            }
          }
        }
      }
    };
  }

  public void start() {
    this.thread.start();
  }
}
