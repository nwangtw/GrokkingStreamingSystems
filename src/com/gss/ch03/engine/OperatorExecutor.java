package com.gss.ch03.engine;

import com.gss.ch03.api.Operator;

/**
 * The executor for operator components. When the executor is started,
 * a new thread is created to call the apply() function of
 * the operator component repeatedly.
 * @param <I> The data type of the events in the incoming event queue
 * @param <O> The data type of the events in the outgoing event queue
 */
public class OperatorExecutor<I, O> {
  private final Operator<I, O> operator;
  protected InstanceExecutor<I, O> [] instanceExecutors;

  public OperatorExecutor(Operator<I, O> operator) {
    this.operator = operator;
    this.instanceExecutors = new OperatorInstanceExecutor[operator.getParallelism()];

    Class operatorClass = operator.getClass();
    for (int i = 0; i < operator.getParallelism(); ++i) {
      instanceExecutors[i] =
          new OperatorInstanceExecutor<I, O>(i, operatorClass.newInstance());
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
}
