package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Source;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SourceRunner<T> implements IComponentRunner<Object, T> {
  private  final int MAX_QUEUE_SIZE = 64;

  private final Source<T> source;

  private final BlockingQueue<T> outgoingStream =
      new ArrayBlockingQueue<T>(MAX_QUEUE_SIZE);

  private final Thread thread;

  public SourceRunner(Source<T> source) {
    this.source = source;
    this.thread = new Thread() {
      public void run() {
        for (;;) {
          runOnce();
        }
      }
    };
  }

  public BlockingQueue<Object> getIncomingStream() {
    throw new RuntimeException("getIncomingStream is not supported by SourceRunner");
  }
  public BlockingQueue<T> getOutgoingStream() {  return outgoingStream; }

  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  boolean runOnce() {
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
