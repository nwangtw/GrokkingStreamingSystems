package com.miniStreaming.ch02.job;

public abstract class Source<T> implements ISource<T> {
  protected Stream<T> outgoingStream = new Stream();

  public Stream<T> getOutgoingStream() { return outgoingStream; }

  // To be implemented.
  public abstract T[] readEvents();
}
