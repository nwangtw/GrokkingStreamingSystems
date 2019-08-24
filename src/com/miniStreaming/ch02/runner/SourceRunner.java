package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Source;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The runner for source components. When the runner is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <T> The data type of the events in the outgoing event queue
 */
public class SourceRunner<T> extends ComponentRunner<Object, T> {
  private final Source<T> source;


  public SourceRunner(Source<T> source) {
    this.source = source;
  }

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
