package com.gss.ch02.engine;

import com.gss.ch02.api.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <T> The data type of the events in the outgoing event queue
 */
public class SourceExecutor<T> extends ComponentExecutor<Object, T> {
  private final Source<T> source;

  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<T> outgoingeEvents =
      new ArrayBlockingQueue<T>(MAX_OUTGOING_QUEUE_SIZE);

  public SourceExecutor(Source<T> source) {
    this.source = source;
  }

  public BlockingQueue<T> getOutgoingQueue() { return outgoingeEvents; }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  protected boolean runOnce() {
    // Generate events
    List<T> collector = new ArrayList<>();
    try {
      source.getEvents(collector);
    } catch (Exception e) {
      return false;  // exit thread
    }

    // Emit out
    for (T event: collector) {
      try {
        getOutgoingQueue().put(event);
      } catch (InterruptedException e) {
        return false;  // exit thread
      }
    }
    return true;
  }
}
