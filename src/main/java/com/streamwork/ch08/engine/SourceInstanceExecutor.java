package com.streamwork.ch08.engine;

import com.streamwork.ch08.api.Event;
import com.streamwork.ch08.api.Source;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceInstanceExecutor extends InstanceExecutor {
  private final int instanceId;
  private final Source source;

  public SourceInstanceExecutor(int instanceId, Source source) {
    this.instanceId = instanceId;
    this.source = source;
    source.setupInstance(instanceId);
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exit.
   */
  protected boolean runOnce() {
    // Generate events
    try {
      source.getEvents(eventCollector);
    } catch (Exception e) {
      return false;  // exit thread
    }

    // Emit out
    try {
      for (String channel: eventCollector.getRegisteredChannels()) {
        for (Event output: eventCollector.getEventList(channel)) {
          for (EventQueue queue: outgoingQueueMap.get(channel)) {
            queue.put(output);
          }
        }
      }
      eventCollector.clear();
    } catch (InterruptedException e) {
      return false;  // exit thread
    }
    return true;
  }
}
