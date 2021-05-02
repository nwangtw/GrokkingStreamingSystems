package com.streamwork.ch07.engine;

import com.streamwork.ch07.api.Source;
import org.apache.commons.lang3.SerializationUtils;

/**
 * The executor for source components. When the executor is started,
 * a new thread is created to call the getEvents() function of
 * the source component repeatedly.
 */
public class SourceExecutor  extends ComponentExecutor {
  public SourceExecutor(Source source) {
    super(source);

    for (int i = 0; i < source.getParallelism(); ++i) {
      Source cloned = SerializationUtils.clone(source);
      instanceExecutors[i] = new SourceInstanceExecutor(i, cloned);
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
  public void setIncomingQueues(EventQueue [] queues) {
    throw new RuntimeException("No incoming queue is allowed for source executor");
  }
}
