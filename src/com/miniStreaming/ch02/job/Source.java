package com.miniStreaming.ch02.job;

public abstract class Source<T> extends com.miniStreaming.ch02.job.Component {
  public abstract T[] readEvents();
}
