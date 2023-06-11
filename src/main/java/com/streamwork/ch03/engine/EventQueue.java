package com.streamwork.ch03.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.streamwork.ch03.api.Event;

/**
 * This is the class for intermediate event queues between processes.
 */
public class EventQueue extends ArrayBlockingQueue<Event> {
  private static final long serialVersionUID = -7123238470714210682L;

  public EventQueue(int size) {
    super(size);
  }
}
