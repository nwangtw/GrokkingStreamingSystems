package com.miniStreaming.ch02.runner;

import java.util.concurrent.BlockingQueue;

public interface IComponentRunner<I, T> {
  BlockingQueue<I> getIncomingStream();
  BlockingQueue<T> getOutgoingStream();

  void start();
}
