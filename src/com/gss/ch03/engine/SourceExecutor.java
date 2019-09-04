package com.gss.ch03.engine;

import com.gss.ch03.api.Source;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <T> The data type of the events in the outgoing event queue
 */
public class SourceExecutor<T> {
  private final Source<T> source;
  protected InstanceExecutor<?, T> [] instanceExecutors;

  public SourceExecutor(Source<T> source) {
    this.source = source;
    this.instanceExecutors = new SourceInstanceExecutor[source.getParallelism()];

    Class sourceClass = source.getClass();
    for (int i = 0; i < source.getParallelism(); ++i) {
      instanceExecutors[i] =
          new SourceInstanceExecutor<T>(i, sourceClass.newInstance());
    }
  }

  @Override
  public void start() {
    if (instanceExecutors != null) {
      for (InstanceExecutor<?, T> executor : instanceExecutors) {
        executor.start();
      }
    }
  }
}
