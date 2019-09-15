package com.gss.ch03.engine;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.Source;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceInstanceExecutor extends InstanceExecutor {
  private final int instanceId;
  private final Source source;
  private final BlockingQueue<Event> outgoingEvents;
  private final ArrayList<Event> eventCollector = new ArrayList<Event>();

  public SourceInstanceExecutor(int instanceId,
                                Source source,
                                BlockingQueue outgoingEvents) {
    this.instanceId = instanceId;
    this.source = source;
    this.outgoingEvents = outgoingEvents;
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    // Generate events
    try {
      source.getEvents(instanceId, eventCollector);
    } catch (Exception e) {
      return false;  // exit thread
    }

    // Emit out
    try {
      for (Event event: eventCollector) {
        outgoingEvents.put(event);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
