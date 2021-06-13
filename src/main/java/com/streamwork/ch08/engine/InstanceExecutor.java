package com.streamwork.ch08.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.streamwork.ch08.api.EventCollector;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public abstract class InstanceExecutor extends Process {
  // This list is used for accepting events from user logic.
  protected final EventCollector eventCollector = new EventCollector();
  // Data queues for the upstream processes
  protected NamedEventQueue incomingQueue = null;
  // Data queue for the downstream processes
  protected Map<String, List<EventQueue>> outgoingQueueMap = new HashMap<String, List<EventQueue>>();

  public InstanceExecutor() { }

  public void registerChannel(String channel) {
    eventCollector.registerChannel(channel);
  }

  public void setIncomingQueue(NamedEventQueue queue) {
    incomingQueue = queue;
  }

  public void addOutgoingQueue(String channel, EventQueue queue) {
    if (outgoingQueueMap.containsKey(channel)) {
      outgoingQueueMap.get(channel).add(queue);
    } else {
      List<EventQueue> queueList = new ArrayList<EventQueue>();
      queueList.add(queue);
      outgoingQueueMap.put(channel, queueList);
    }
  }
}
