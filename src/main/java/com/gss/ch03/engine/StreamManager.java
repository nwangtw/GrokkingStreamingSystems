package com.gss.ch03.engine;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;

import java.util.List;

/**
 * StreamManager is responsible for transporting events from
 * the outgoing event queue of the incoming (upstream) component to
 * the incoming event queue of the (outgoing) downstream component.
 */
public class StreamManager {
  private final ComponentExecutor incoming;
  private final List<OperatorExecutor> outgoingList;
  private final Thread thread;

  public StreamManager(ComponentExecutor incoming,
                       List<OperatorExecutor> outgoingList) {
    this.incoming = incoming;
    this.outgoingList = outgoingList;
    this.thread = new Thread() {
      public void run() {
        while (runOnce());
      }
    };
  }

  public void start() {
    this.thread.start();
  }
  private boolean runOnce() {
    try {
      Event event = incoming.getOutgoingQueue().take();
      System.out.println("****event");
      for (OperatorExecutor outgoing: outgoingList) {
        IGroupingStrategy grouping = outgoing.getGroupingStrategy();
        int instance = grouping.getInstance(event, outgoing.getParallelism());
        outgoing.getIncomingQueue(instance).put(event);
      }
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }
}
