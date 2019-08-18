package com.miniStreaming.ch02.job;

import java.util.HashMap;
import java.util.Map;

public class Stream<T> {
  // List of all operations to be applied to this stream.
  private final Map<String, Operator<T, ?>> operationMap =
      new HashMap<String, Operator<T, ?>>();

  public <O> Stream applyOperation(String operationName, Operator<T, O> operation) {
    if (operationMap.containsKey(operationName)) {
      throw new RuntimeException("Operation " + operationName + " already exists!");
    }
    operationMap.put(operationName, operation);
    return operation.getOutgoingStream();
  }

  public Map<String, Operator<T, ?>> getOperationMap() {
    return operationMap;
  }
}
