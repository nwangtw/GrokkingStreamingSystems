package com.gss.ch02.engine;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public abstract class ComponentExecutor<I, O> implements IComponentExecutor<I, O> {
  private final Thread thread;

  public ComponentExecutor() {
    this.thread = new Thread() {
      public void run() {
        while (runOnce());
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
