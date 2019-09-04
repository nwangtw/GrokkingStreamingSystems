package com.gss.ch03.engine;

import com.google.gson.Gson;
import com.gss.ch03.api.Source;

import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 * @param <T> The data type of the events in the outgoing event queue
 */
public class SourceExecutor<T>  extends ComponentExecutor<Object, T> {
  private final Source<T> source;
  private final InstanceExecutor<?, T> [] instanceExecutors;
  private final BlockingQueue<T> [] outgoingQueueArray;

  public SourceExecutor(Source<T> source) {
    this.source = source;
    this.instanceExecutors = new SourceInstanceExecutor[source.getParallelism()];
    this.outgoingQueueArray = new BlockingQueue[source.getParallelism()];

    Class sourceClass = source.getClass();
    Gson gson = new Gson();
    for (int i = 0; i < source.getParallelism(); ++i) {
      Source<T> cloned = gson.fromJson(gson.toJson(source), source.getClass());
      instanceExecutors[i] = new SourceInstanceExecutor<T>(i, cloned);
      outgoingQueueArray[i] = instanceExecutors[i].getOutgoingQueue();
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

  @Override
  public BlockingQueue<T>[] getOutgoingQueueArray() { return outgoingQueueArray; }
}
