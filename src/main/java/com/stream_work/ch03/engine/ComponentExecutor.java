package com.stream_work.ch03.engine;

import java.util.concurrent.BlockingQueue;

import com.stream_work.ch03.api.Component;
import com.stream_work.ch03.api.Event;

/**
 * The base class for executors of source and operator.
 */
public abstract class ComponentExecutor {
  protected Component component;
  protected InstanceExecutor [] instanceExecutors;

  public ComponentExecutor(Component component) {
    this.component = component;
    int parallelism = component.getParallelism();
    this.instanceExecutors = new InstanceExecutor[parallelism];
  }

  /**
   * Start instance executors (real processes) of this component.
   */
  public abstract void start();

  /**
   * Get the instance executors of this component executor.
   */
  public InstanceExecutor [] getInstanceExecutors() {
    return instanceExecutors;
  }

  public Component getComponent() {
    return component;
  }

  public void setIncomingQueues(BlockingQueue<Event> [] queues) {
    for (int i = 0; i < queues.length; ++i) {
      instanceExecutors[i].setIncomingQueue(queues[i]);
    }
  }

  public void setOutgoingQueue(BlockingQueue<Event> queue) {
    for (InstanceExecutor instance: instanceExecutors) {
      instance.setOutgoingQueue(queue);
    }
  }
}
