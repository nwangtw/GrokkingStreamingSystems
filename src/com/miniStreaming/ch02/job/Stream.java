package com.miniStreaming.ch02.job;

import java.util.HashMap;
import java.util.Map;

public class Stream<T> {
  // List of all operations to be applied to this stream.
  private final Map<String, Operator<T, ?>> operationMap =
      new HashMap<String, Operator<T, ?>>();

  public <O> Stream applyOperator(String operationName, Operator<T, O> operator) {
    if (operationMap.containsKey(operationName)) {
      throw new RuntimeException("Operation " + operationName + " already exists!");
    }
    operationMap.put(operationName, operator);
    return operator.getOutgoingStream();
  }

  public Map<String, Operator<T, ?>> getOperationMap() {
    return operationMap;
  }
}
