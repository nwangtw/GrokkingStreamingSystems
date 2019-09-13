package com.gss.ch02.api;

import java.util.ArrayList;
import java.util.List;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 */
public class Stream {
  // List of all operators to be applied to this stream.
  private final List<Operator> operationList =
      new ArrayList<Operator>();

  /**
   * Apply an operator to this stream.
   * @param operator The operator to be connected to the current stream
   * @return The outgoing stream of the operator.
   */
  public Stream applyOperator(Operator operator) {
    operationList.add(operator);
    return operator.getOutgoingStream();
  }

  /**
   * Get the list of operators applied to this stream.
   * @return The list of operators applied to this stream
   */
  public List<Operator> getOperationList() {
    return operationList;
  }
}
