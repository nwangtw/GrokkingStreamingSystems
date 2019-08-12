package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Source;

public class SourceRunner<T> extends ComponentRunner<T> {
  private final Source<T> source;

  public SourceRunner(Source<T> source) {
    super(source);
    this.source = source;
  }

  @Override
  public boolean runOnce() {
    // Generate data
    T[] events;
    try {
      events = source.readEvents();
    } catch (Exception e) {
      return false;  // exit thread
    }
    // Send out
    for (T event: events) {
      try {
        outgoingStream.put(event);
      } catch (InterruptedException e) {
        return false;  // exit thread
      }
    }
    return true;
  }
}
