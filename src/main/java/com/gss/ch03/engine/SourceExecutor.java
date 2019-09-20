package com.gss.ch03.engine;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.Source;
import org.apache.commons.lang3.SerializationUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceExecutor  extends ComponentExecutor {
  private final Source source;
  private final InstanceExecutor [] instanceExecutors;

  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<Event> outgoingEvents =
      new ArrayBlockingQueue<Event>(MAX_OUTGOING_QUEUE_SIZE);

  public SourceExecutor(Source source) {
    this.source = source;
    this.instanceExecutors = new SourceInstanceExecutor[source.getParallelism()];

    for (int i = 0; i < source.getParallelism(); ++i) {
      Source cloned = SerializationUtils.clone(source);
      cloned.setupInstance(i);
      instanceExecutors[i] = new SourceInstanceExecutor(i, cloned, outgoingEvents);
    }
  }

  @Override
  public void start() {
    if (instanceExecutors != null) {
      for (InstanceExecutor executor : instanceExecutors) {
        executor.start();
      }
    }
  }

  @Override
  public BlockingQueue<Event> getOutgoingQueue() { return outgoingEvents; }
}
