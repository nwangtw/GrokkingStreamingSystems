package com.gss.ch03.engine;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * StreamManager is responsible for transporting events from
 * the outgoing event queue of the upstream component to
 * the incoming event queue of the downstream component.
 * @param <T> The data type of the events in the queues
 */
public class StreamManager<T> {
  private final BlockingQueue<T>[] incomingQueueArray;
  private final List<BlockingQueue<T>[]> outgoingQueueArrayList;
  private final Thread thread;

  public StreamManager(BlockingQueue<T>[] incomingQueue,
                       List<BlockingQueue<T>[]> outgoingQueueList) {
    this.incomingQueueArray = incomingQueue;
    this.outgoingQueueArrayList = outgoingQueueList;
    this.thread = new Thread() {
      public void run() {
        while (true) {
          T event;
          try {
            event = incomingQueueArray[0].take();  // TODO
          } catch (InterruptedException e) {
            return;
          }
          for (BlockingQueue<T>[] queue: outgoingQueueList) {
            try {
              queue[0].put(event);  // TODO
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
