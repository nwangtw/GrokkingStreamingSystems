package com.stream_work.ch03.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.stream_work.ch03.api.Event;

/**
 * This is the class for intemediate event queues between processes.
 */
public class EventQueue extends ArrayBlockingQueue<Event> {
  public EventQueue(int size) {
    super(size);
  }
}