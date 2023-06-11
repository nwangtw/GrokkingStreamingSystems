package com.streamwork.ch02.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.streamwork.ch02.api.Event;

/**
 * This is the class for intermediate event queues between processes.
 */
public class EventQueue extends ArrayBlockingQueue<Event> {
  private static final long serialVersionUID = 3673430816396878407L;

  public EventQueue(int size) {
      super(size);
  }
}
