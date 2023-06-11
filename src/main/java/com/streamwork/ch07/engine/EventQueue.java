package com.streamwork.ch07.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.streamwork.ch07.api.Event;

/**
 * This is the class for intermediate event queues between processes.
 */
public class EventQueue extends ArrayBlockingQueue<Event> {
  private static final long serialVersionUID = 5299563454777716488L;

  public EventQueue(int size) {
    super(size);
  }
}
