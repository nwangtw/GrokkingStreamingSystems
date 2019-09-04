package com.gss.ch03.engine;

import com.gss.ch03.api.Source;

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

  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<T> outgoingeEvents =
      new ArrayBlockingQueue<T>(MAX_OUTGOING_QUEUE_SIZE);

  public SourceInstanceExecutor(int instanceId, Source<T> source) {
    this.instanceId = instanceId;
    this.source = source;
  }

  public BlockingQueue<T> getOutgoingQueue() { return outgoingeEvents; }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    // Generate data
    T[] events;
    try {
      events = source.getEvents();
    } catch (Exception e) {
      return false;  // exit thread
    }
    // Send out
    for (T event: events) {
      try {
        getOutgoingQueue().put(event);
      } catch (InterruptedException e) {
        return false;  // exit thread
      }
    }
    return true;
  }
}
