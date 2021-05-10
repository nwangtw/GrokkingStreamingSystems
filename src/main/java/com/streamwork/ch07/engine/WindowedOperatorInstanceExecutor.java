package com.streamwork.ch07.engine;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.streamwork.ch07.api.Event;
import com.streamwork.ch07.api.EventWindow;
import com.streamwork.ch07.api.WindowedOperator;
import com.streamwork.ch07.api.WindowingStrategy;

/**
 * The executor for windowed operator components. It works slightly different from
 * regular operator instance executor. A windowing strategy needs to be applied.
 */
public class WindowedOperatorInstanceExecutor extends OperatorInstanceExecutor {
  private final WindowedOperator windowedOperator;
  private final WindowingStrategy windowingStrategy;

  public WindowedOperatorInstanceExecutor(int instanceId, WindowedOperator operator, WindowingStrategy windowingStrategy) {
    super (instanceId, operator);
    this.windowedOperator = operator;
    this.windowingStrategy = windowingStrategy;
    operator.setupInstance(instanceId);
  }

  /**
   * Run process once.
   * @return true if the thread should continue; false if the thread should exist.
   */
  @Override
  protected boolean runOnce() {
    Event event;
    try {
      // Read input. Time out every one second to check if there is any event windows ready to be processed.
      event = incomingQueue.poll(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      return false;
    }

    // Windowed operator handles events differently.
    long processingTime = System.currentTimeMillis();
    if (event != null) {
      windowingStrategy.add(event, processingTime);
    }

    List<EventWindow> windows = windowingStrategy.getEventWindows(processingTime);
    for (EventWindow window: windows) {
      windowedOperator.apply(window, eventCollector);
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
