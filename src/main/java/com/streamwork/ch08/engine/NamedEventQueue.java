package com.streamwork.ch08.engine;

import java.util.concurrent.ArrayBlockingQueue;

import com.streamwork.ch08.api.NameEventPair;

/**
 * This is the class for intermediate event queues between processes.
 */
public class NamedEventQueue extends ArrayBlockingQueue<NameEventPair> {
  public final String streamName;

  public NamedEventQueue(int size, String streamName) {
    super(size);
    this.streamName = streamName;
  }
}
