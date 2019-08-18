package com.miniStreaming.ch02.job;

public abstract class Operator<I, T> implements IOperator<I, T> {
  protected Stream<I> incomingStream = new Stream<I>();
  protected Stream<T> outgoingStream = new Stream<T>();

  public  Stream<I> getIncomingStream() { return incomingStream; }
  public Stream<T> getOutgoingStream() { return outgoingStream; }

  public abstract T[] apply(I event);
}
