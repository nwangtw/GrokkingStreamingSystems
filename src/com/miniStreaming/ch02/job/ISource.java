package com.miniStreaming.ch02.job;

public interface ISource<T> extends IComponent<Object, T> {
  default Stream<Object> getIncomingStream() {
    throw new RuntimeException("getIncomingStream is not supported by Source");
  }
  Stream<T> getOutgoingStream();
}
