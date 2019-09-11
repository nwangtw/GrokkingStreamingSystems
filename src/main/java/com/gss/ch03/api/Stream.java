package com.gss.ch03.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * @param <T> The data type of the events in the this stream
 */
public class Stream<T> implements Serializable {
  // List of all operators to be applied to this stream.
  private final List<Operator<T, ?>> operationList =
      new ArrayList<Operator<T, ?>>();

  /**
   * Connect an operator to this stream.
   * @param operator The operator to be connected to the current stream
   * @param <O> The data type of the events in the results
   * @return The outgoing stream of the operator.
   */
  public <O> Stream<O> applyOperator(Operator<T, O> operator) {
    operationList.add(operator);
    return operator.getOutgoingStream();
  }

  /**
   * Get the list of operators applied to this stream.
   * @return The list of operators applied to this stream
   */
  public List<Operator<T, ?>> getOperationList() {
    return operationList;
  }
}
