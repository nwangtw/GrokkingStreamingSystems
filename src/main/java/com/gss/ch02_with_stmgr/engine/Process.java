package com.gss.ch02_with_stmgr.engine;

import com.gss.ch02.api.Event;

import java.util.concurrent.BlockingQueue;

/**
 * This is the base class of all processes, including executors and stream managers.
 * When a process is started, a new thread is created to call the runOnce() function of
 * the derived class. Each process also have an incoming event queue and an outgoing
 * event queue.
 */
public abstract class Process {
  private final Thread thread;

  // Data queues for the upstream processes
  protected BlockingQueue<Event> incomingQueue;
  // Data queue for the downstream processes
  protected BlockingQueue<Event> outgoingQueue;

  public Process() {
    this.thread = new Thread() {
      public void run() {
        while (runOnce());
      }
    };
  }

  public void setIncomingQueue(BlockingQueue<Event> queue) {
    incomingQueue = queue;
  }

  public void setOutgoingQueue(BlockingQueue<Event> queue) {
    outgoingQueue = queue;
  }

  /**
   * Start the process.
   */
  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  abstract boolean runOnce();
}
