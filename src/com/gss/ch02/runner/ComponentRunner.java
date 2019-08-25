package com.gss.ch02.runner;

/**
 * The runner for source components. When the runner is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public abstract class ComponentRunner<I, O> implements IComponentRunner<I, O> {
  private final Thread thread;

  public ComponentRunner() {
    this.thread = new Thread() {
      public void run() {
        for (;;) {
          runOnce();
        }
      }
    };
  }

  public void start() {
    thread.start();
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  abstract boolean runOnce();
}
