package com.gss.ch03.engine;

import com.gss.ch03.api.Source;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <T> The data type of the events in the outgoing event queue
 */
public class SourceInstanceExecutor<T> extends InstanceExecutor<Object, T> {
  private final int instanceId;
  private final Source<T> source;
  private final BlockingQueue<T> outgoingEvents;
  private final ArrayList<T> eventCollector = new ArrayList<>();

  public SourceInstanceExecutor(int instanceId,
                                Source<T> source,
                                BlockingQueue<T> outgoingEvents) {
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
      source.getEvents(eventCollector);
    } catch (Exception e) {
      return false;  // exit thread
    }

    // Emit out
    try {
      for (T event: eventCollector) {
        outgoingEvents.put(event);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
