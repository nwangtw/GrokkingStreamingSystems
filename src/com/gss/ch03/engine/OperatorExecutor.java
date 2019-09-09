package com.gss.ch03.engine;

import com.google.gson.Gson;
import com.gss.ch03.api.GroupingStrategy;
import com.gss.ch03.api.Operator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public class OperatorExecutor<I, O> extends ComponentExecutor<I, O> {
  private final Operator<I, O> operator;
  private final int parallelism;
  protected InstanceExecutor<I, O> [] instanceExecutors;

  private final int MAX_INCOMNG_QUEUE_SIZE = 64;
  private final int MAX_OUTGOING_QUEUE_SIZE = 64;
  private final BlockingQueue<O> outgoingEvents =
      new ArrayBlockingQueue<O>(MAX_OUTGOING_QUEUE_SIZE);
  private final BlockingQueue<I>[] incomingEventsArray;

  public OperatorExecutor(Operator<I, O> operator) {
    this.operator = operator;
    this.parallelism = operator.getParallelism();
    this.instanceExecutors = new OperatorInstanceExecutor[parallelism];
    this.incomingEventsArray = new BlockingQueue[parallelism];

    Gson gson = new Gson();
    for (int i = 0; i < parallelism; ++i) {
      Operator<I, O> cloned = gson.fromJson(gson.toJson(operator), operator.getClass());
      incomingEventsArray[i] = new ArrayBlockingQueue<I>(MAX_INCOMNG_QUEUE_SIZE);
      OperatorInstanceExecutor<I, O> operatorInstanceExecutor =
          new OperatorInstanceExecutor<I, O>(i, cloned, incomingEventsArray[i], outgoingEvents);
      instanceExecutors[i] = operatorInstanceExecutor;
    }
  }

  @Override
  public void start() {
    if (instanceExecutors != null) {
      for (InstanceExecutor<I, O> executor : instanceExecutors) {
        executor.start();
      }
    }
  }

  public BlockingQueue<I> getIncomingQueueByKey(int key) {
    return incomingEventsArray[key % parallelism];
  }

  @Override
  public BlockingQueue<O> getOutgoingQueue() { return outgoingEvents; }

  public GroupingStrategy getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
