package com.miniStreaming.ch02.job;

import java.util.HashMap;
import java.util.Map;

public class Stream {
  // List of all operations to be applied
  private final Map<String, com.miniStreaming.ch02.job.Operation> operationMap;

  public Stream() {
    this.operationMap = new HashMap<String, com.miniStreaming.ch02.job.Operation>();
  }

  public Stream applyOperation(String operationName, com.miniStreaming.ch02.job.Operation operation) {
    if (operationMap.containsKey(operationName)) {
      throw new RuntimeException("Operation " + operationName + " already exists!");
    }
    operationMap.put(operationName, operation);
    return operation.getOutgoingStream();
  }

  public Map<String, com.miniStreaming.ch02.job.Operation> getOperationMap() {
    return operationMap;
  }
}
