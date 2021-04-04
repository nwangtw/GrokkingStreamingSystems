package com.streamwork.ch03.engine;

import java.util.ArrayList;
import java.util.List;

import com.streamwork.ch03.api.Event;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public abstract class InstanceExecutor extends Process {
  // This list is used for accepting events from user logic.
  protected final List<Event> eventCollector = new ArrayList<Event>();
  // Data queues for the upstream processes
  protected EventQueue incomingQueue = null;
  // Data queue for the downstream processes
  protected EventQueue outgoingQueue = null;

  public InstanceExecutor() { }

  public void setIncomingQueue(EventQueue queue) {
    incomingQueue = queue;
  }

  public void setOutgoingQueue(EventQueue queue) {
    outgoingQueue = queue;
  }
}
