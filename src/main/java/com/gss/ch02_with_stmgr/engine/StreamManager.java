package com.gss.ch02_with_stmgr.engine;

import com.gss.ch02.api.Event;

/**
 * StreamManager is a process in the engine responsible for transporting
 * events from the incoming queue to the outgoing queue repeatly.
 * When the stream manager is started, a thread is created to
 */
public class StreamManager extends Process {

  public StreamManager() {
    super();
  }

  @Override
  boolean runOnce() {
    try {
      Event event = incomingQueue.take();
      outgoingQueue.put(event);
    } catch (InterruptedException e) {
      return false;
    }
    return true;
  }

  
  
}
