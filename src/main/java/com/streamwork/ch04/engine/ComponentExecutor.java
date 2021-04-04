package com.streamwork.ch04.engine;

import com.streamwork.ch04.api.Component;

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

  public void registerChannel(String channel) {
    for (InstanceExecutor instance: instanceExecutors) {
      instance.registerChannel(channel);
    }
  }

  public void setIncomingQueues(EventQueue [] queues) {
    for (int i = 0; i < queues.length; ++i) {
      instanceExecutors[i].setIncomingQueue(queues[i]);
    }
  }

  public void addOutgoingQueue(String channel, EventQueue queue) {
    for (InstanceExecutor instance: instanceExecutors) {
      instance.addOutgoingQueue(channel, queue);
    }
  }
}
