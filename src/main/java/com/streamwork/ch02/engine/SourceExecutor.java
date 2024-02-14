package com.streamwork.ch02.engine;

import com.streamwork.ch02.api.Event;
import com.streamwork.ch02.api.Source;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceExecutor extends ComponentExecutor {
  private final Source source;

  public SourceExecutor(Source source) {
    super(source);
    this.source = source;
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exit.
   */
  @Override
  boolean runOnce() {
    // Generate events
    try {
      source.getEvents(eventCollector);
    } catch (Exception e) {
      return false;  // exit thread
    }

    // Emit out
    try {
      for (Event event: eventCollector) {
        outgoingQueue.put(event);
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }

  @Override
  public void setIncomingQueue(EventQueue queue) {
    throw new RuntimeException("No incoming queue is allowed for source executor");
  }
}
