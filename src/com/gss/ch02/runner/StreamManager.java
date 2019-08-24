package com.gss.ch02.runner;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * StreamManager is responsible for transporting events from
 * the outgoing event queue of the upstream component to
 * the incoming event queue of the downstream component.
 * @param <T> The data type of the events in the queues
 */
public class StreamManager<T> {
  private final BlockingQueue<T> incomingQueue;
  private final List<BlockingQueue<T>> outgoingQueueList;
  private final Thread thread;

  public StreamManager(BlockingQueue<T> incomingQueue,
                       List<BlockingQueue<T>> outgoingQueueList) {
    this.incomingQueue = incomingQueue;
    this.outgoingQueueList = outgoingQueueList;
    this.thread = new Thread() {
      public void run() {
        while (true) {
          T event;
          try {
            event = incomingQueue.take();
          } catch (InterruptedException e) {
            return;
          }
          for (BlockingQueue<T> queue: outgoingQueueList) {
            try {
              queue.put(event);
            } catch (InterruptedException e) {
              return;
            }
          }
        }
      }
    };
  }

  public void start() {
    this.thread.start();
  }
}
