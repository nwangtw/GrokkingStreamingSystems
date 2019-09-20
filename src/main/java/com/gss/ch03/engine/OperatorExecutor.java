package com.gss.ch03.engine;

import com.gss.ch03.api.Event;
import com.gss.ch03.api.IGroupingStrategy;
import com.gss.ch03.api.Operator;
import org.apache.commons.lang3.SerializationUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 */
public class OperatorExecutor extends ComponentExecutor {
  private final Operator operator;
  private final int parallelism;
  protected InstanceExecutor [] instanceExecutors;

  private final int MAX_INCOMNG_QUEUE_SIZE = 64;
  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<Event> outgoingEvents =
      new ArrayBlockingQueue<Event>(MAX_OUTGOING_QUEUE_SIZE);
  private final BlockingQueue<Event>[] incomingEventsArray;

  public OperatorExecutor(Operator operator) {
    this.operator = operator;
    this.parallelism = operator.getParallelism();
    this.instanceExecutors = new OperatorInstanceExecutor[parallelism];
    this.incomingEventsArray = new BlockingQueue[parallelism];

    for (int i = 0; i < parallelism; ++i) {
      Operator cloned = SerializationUtils.clone(operator);
      cloned.setupInstance(i);
      incomingEventsArray[i] = new ArrayBlockingQueue<Event>(MAX_INCOMNG_QUEUE_SIZE);
      OperatorInstanceExecutor operatorInstanceExecutor =
          new OperatorInstanceExecutor(i, cloned, incomingEventsArray[i], outgoingEvents);
      instanceExecutors[i] = operatorInstanceExecutor;
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

  public BlockingQueue<Event> getIncomingQueueByKey(int key) {
    return incomingEventsArray[key % parallelism];
  }

  @Override
  public BlockingQueue<Event> getOutgoingQueue() {
    return outgoingEvents;
  }

  public IGroupingStrategy getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
