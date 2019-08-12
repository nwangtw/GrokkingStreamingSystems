package com.miniStreaming.ch02.job;

public abstract class Operation<I, T> extends com.miniStreaming.ch02.job.Component {
  public abstract T[] apply(I event);
}
