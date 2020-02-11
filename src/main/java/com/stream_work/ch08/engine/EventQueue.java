package com.stream_work.ch08.engine;

import com.stream_work.ch08.api.Event;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * This is the class for intermediate event queues between processes.
 */
public class EventQueue extends ArrayBlockingQueue<Event> {

  private static final long serialVersionUID = 7632623898828913064L;

  public EventQueue(int size) {
    super(size);
  }
}