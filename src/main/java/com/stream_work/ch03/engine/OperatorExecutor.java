package com.stream_work.ch03.engine;

import com.stream_work.ch03.api.IGroupingStrategy;
import com.stream_work.ch03.api.Operator;
import org.apache.commons.lang3.SerializationUtils;

/**
 * The executor for operator components. When the executor is started, a new thread
 * is created to call the apply() function of the operator component repeatedly.
 */
public class OperatorExecutor extends ComponentExecutor {
  public OperatorExecutor(Operator operator) {
    super(operator);

    for (int i = 0; i < operator.getParallelism(); ++i) {
      Operator cloned = SerializationUtils.clone(operator);
      instanceExecutors[i] = new OperatorInstanceExecutor(i, cloned);
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

  public IGroupingStrategy getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
