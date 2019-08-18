package com.miniStreaming.ch02.job;

public interface IComponent<I, T> {
  Stream<I> getIncomingStream();
  Stream<T> getOutgoingStream();
}