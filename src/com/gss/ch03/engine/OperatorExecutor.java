package com.gss.ch03.engine;

import com.google.gson.Gson;
import com.gss.ch03.api.Operator;
import com.gss.ch03.api.Source;

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
  protected InstanceExecutor<I, O> [] instanceExecutors;
  private final BlockingQueue<I> [] incomingQueueArray;
  private final BlockingQueue<O> [] outgoingQueueArray;

  public OperatorExecutor(Operator<I, O> operator) {
    this.operator = operator;
    this.instanceExecutors = new OperatorInstanceExecutor[operator.getParallelism()];
    this.incomingQueueArray = new BlockingQueue[operator.getParallelism()];
    this.outgoingQueueArray = new BlockingQueue[operator.getParallelism()];

    Gson gson = new Gson();
    for (int i = 0; i < operator.getParallelism(); ++i) {
      Operator<I, O> cloned = gson.fromJson(gson.toJson(operator), operator.getClass());
      OperatorInstanceExecutor<I, O> operatorInstanceExecutor =
          new OperatorInstanceExecutor<I, O>(i, cloned);
      incomingQueueArray[i] = operatorInstanceExecutor.getIncomingQueue();
      outgoingQueueArray[i] = operatorInstanceExecutor.getOutgoingQueue();
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

  public BlockingQueue<I>[] getIncomingQueueArray() { return incomingQueueArray; }

  @Override
  public BlockingQueue<O>[] getOutgoingQueueArray() { return outgoingQueueArray; }
}
