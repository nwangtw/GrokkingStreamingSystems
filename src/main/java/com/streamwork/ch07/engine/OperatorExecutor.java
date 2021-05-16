package com.streamwork.ch07.engine;


import com.streamwork.ch07.api.GroupingStrategy;
import com.streamwork.ch07.api.Operator;
import org.apache.commons.lang3.SerializationUtils;

/**
 * The executor for operator components. When the executor is started, a new thread
 * is created to call the apply() function of the operator component repeatedly.
 */
public class OperatorExecutor extends ComponentExecutor {
  private Operator operator;
  public OperatorExecutor(Operator operator) {
    super(operator);

    this.operator = operator;
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

  public GroupingStrategy getGroupingStrategy() {
    return operator.getGroupingStrategy();
  }
}
