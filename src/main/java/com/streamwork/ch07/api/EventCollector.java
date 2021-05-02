package com.streamwork.ch07.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is a collector to accept events from components.
 * Component can specific a channel name when adding events.
 * @param channel
 * @param event
 */
public class EventCollector {
  private static final String DEFAULT_CHANNEL = "default";

  private final Map<String, List<Event>> queues = new HashMap<String, List<Event>>();
  private final Set<String> registeredChannels = new HashSet<String>();

  public EventCollector() {
  }

  public void registerChannel(String channel) {
    registeredChannels.add(channel);
    queues.put(channel, new ArrayList<Event>());
  }

  public void add(Event event) {
    add(DEFAULT_CHANNEL, event);
  }

  public void add(String channel, Event event) {
    // If the channel is registered, add the event to the corresponding list.
    if (queues.containsKey(channel)) {
      queues.get(channel).add(event);
    }
  }

  public Set<String> getRegisteredChannels() {
      return registeredChannels;
  }

  public List<Event> getEventList(String channel) {
    return queues.get(channel);
  }

  public void clear() {
      for (List<Event> queue: queues.values()) {
          queue.clear();
      }
  }
}
