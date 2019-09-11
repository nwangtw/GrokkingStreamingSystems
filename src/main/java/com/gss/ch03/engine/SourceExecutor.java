package com.gss.ch03.engine;

import com.gss.ch03.api.Source;
import org.apache.commons.lang3.SerializationUtils;

import java.util.concurrent.ArrayBlockingQueue;
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

  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<T> outgoingEvents =
      new ArrayBlockingQueue<T>(MAX_OUTGOING_QUEUE_SIZE);

  public SourceExecutor(Source<T> source) {
    this.source = source;
    this.instanceExecutors = new SourceInstanceExecutor[source.getParallelism()];

    for (int i = 0; i < source.getParallelism(); ++i) {
      Source<T> cloned = SerializationUtils.clone(source);
      cloned.setup();
      instanceExecutors[i] = new SourceInstanceExecutor<T>(i, cloned, outgoingEvents);
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
  public BlockingQueue<T> getOutgoingQueue() { return outgoingEvents; }
}
