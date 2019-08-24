package com.miniStreaming.ch02.job;

import java.util.HashMap;
import java.util.Map;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * @param <T> The data type of the events in the this stream
 */
public class Stream<T> {
  // Map of all operators to be applied to this stream.
  private final Map<String, Operator<T, ?>> operationMap =
      new HashMap<String, Operator<T, ?>>();

  /**
   * Connect an operator to this stream.
   * @param operatorName The name of the operator in a job
   * @param operator The operator to be connected to the current stream
   * @param <O> The data type of the events in the results
   * @return The outgoing stream of the operator.
   */
  public <O> Stream<O> applyOperator(String operatorName, Operator<T, O> operator) {
    if (operationMap.containsKey(operatorName)) {
      throw new RuntimeException("Operation " + operatorName + " already exists!");
    }
    operationMap.put(operatorName, operator);
    return operator.getOutgoingStream();
  }

  /**
   * Get the map of name -> operator stored in this stream.
   * @return The map of name -> operator
   */
  public Map<String, Operator<T, ?>> getOperationMap() {
    return operationMap;
  }
}
