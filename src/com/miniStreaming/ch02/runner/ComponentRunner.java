package com.miniStreaming.ch02.runner;

import com.miniStreaming.ch02.job.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ComponentRunner<T> {
  protected final int MAX_QUEUE_SIZE = 64;
  protected final BlockingQueue<T> outgoingStream;

  private final Thread thread;

  public ComponentRunner(Component component) {
    this.outgoingStream = new ArrayBlockingQueue<T>(MAX_QUEUE_SIZE);
    this.thread = new Thread() {
      public void run() {
        for (;;) {
          runOnce();
        }
      }
    };
  }

  public BlockingQueue<T> getOutgoingStream() {
    return outgoingStream;
  }

  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  public boolean runOnce() { return true; }
}
