package com.gss.ch02.engine;

import com.gss.ch02.api.Event;
import com.gss.ch02.api.Source;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceExecutor extends ComponentExecutor {
  private final Source source;

  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<Event> outgoingEvents =
      new ArrayBlockingQueue<Event>(MAX_OUTGOING_QUEUE_SIZE);
  private final ArrayList<Event> eventCollector = new ArrayList<Event>();

  public SourceExecutor(Source source) {
    this.source = source;
  }

  public BlockingQueue<Event> getOutgoingQueue() { return outgoingEvents; }

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
      for (Event event: eventCollector) {
        getOutgoingQueue().put(event);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
