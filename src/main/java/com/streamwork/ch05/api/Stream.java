package com.streamwork.ch05.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Stream class represents a data stream coming out of a component.
 * Operators with the correct type can be applied to this stream.
 * Example:
 *   Job job = new Job("my_job");
 *   job.addSource(mySource)
 *      .applyOperator(myOperator);
 */
public class Stream implements Serializable {
  private static final long serialVersionUID = -4375024141519119762L;
  private static final String DEFAULT_CHANNEL = "default";

  // List of all operators to be applied to channels in this stream.
  private final Map<String, Set<Operator>> operatorMap = new HashMap<String, Set<Operator>>();

  public Stream applyOperator(Operator operator) {
    return applyOperator(DEFAULT_CHANNEL, operator);
  }

  /**
   * Apply an operator to this stream.
   * @param channel The channel to hook up the operator.
   * @param operator The operator to be connected to the current stream
   * @return The outgoing stream of the operator.
   */
  protected Stream applyOperator(String channel, Operator operator) {
    if (operatorMap.containsKey(channel)) {
      Set<Operator> operatorSet = operatorMap.get(channel);
      if (operatorSet.contains(operator)) {
        throw new RuntimeException("Operator " + operator.getName() + " is added to job twice");
      }
      operatorSet.add(operator);
    } else {
        // This is a new channel.
      Set<Operator> operatorSet = new HashSet<Operator>();
      operatorSet.add(operator);
      operatorMap.put(channel, operatorSet);
    }

    return operator.getOutgoingStream();
  }

  public StreamChannel selectChannel(String channel) {
    return new StreamChannel(this, channel);
  }

  /**
   * Get the channels in the stream. Note that the channel set
   * is collected from the downstream component's applyOperator() calls.
   * @return All the channel names registered by the downstream operator.
   */
  public Set<String> getChannels() {
    return operatorMap.keySet();
  }

  /**
   * Get the collection of operators applied to this stream.
   * @return The collection of operators applied to this stream
   */
  public Collection<Operator> getAppliedOperators(String channel) {
    return operatorMap.get(channel);
  }
}
