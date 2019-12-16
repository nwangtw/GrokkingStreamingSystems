package com.stream_work.ch02.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.stream_work.ch02.api.Event;

/**
 * The base class for executors of source and operator.
 */
public abstract class ComponentExecutor extends Process {
  // This list is used for accepting events from user logic.
  protected final List<Event> eventCollector = new ArrayList<Event>();
  // Data queues for the upstream processes
  protected EventQueue incomingQueue = null;
  // Data queue for the downstream processes
  protected EventQueue outgoingQueue = null;

  public ComponentExecutor() { }

  public void setIncomingQueue(EventQueue queue) {
    incomingQueue = queue;
  }

  public void setOutgoingQueue(EventQueue queue) {
    outgoingQueue = queue;
  }
}
