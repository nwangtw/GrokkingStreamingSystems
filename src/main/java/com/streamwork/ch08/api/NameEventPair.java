package com.streamwork.ch08.api;

/**
 * This is the data structure of event with a stream name.
 * It is a special type of event and it is used by JoinOperator
 * (between event dispatcher and operator instance executor).
 */
public class NameEventPair implements Event {
  private final String streamName;
  private final Event event;

  public NameEventPair(String streamName, Event event) {
    this.streamName = streamName;
    this.event = event;
  }

  public String getStreamName() {
    return streamName;
  }

  public Event getEvent() {
    return event;
  }

  @Override
  public String toString() {
    return String.format("[stream name:%s; event:%s]", streamName, event.toString());
  }
}
