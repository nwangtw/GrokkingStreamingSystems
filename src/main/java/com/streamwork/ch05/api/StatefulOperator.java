package com.streamwork.ch05.api;

/**
 * This Operator class is the base class for all user defined stateful operators.
 */
public abstract class StatefulOperator extends Operator {
  private static final long serialVersionUID = -1339819357989957259L;

  public StatefulOperator(String name, int parallelism) {
    super(name, parallelism);
  }

  public StatefulOperator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism, grouping);
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   * @param state The state object used to construct the instance.
   */
  public abstract void setupInstance(int instance, State state);

  /**
   * The original instance setup function shouldn't be used any more.
   */
  public final void setupInstance(int instance) {
    throw new RuntimeException("Stateful component should be set up with a state object");
  }

  /**
   * Get the current state of the component. The state object can be used later
   * to reconstruct the component if a job recovery or rollback happens.
   * @return the current state of the component.
   */
  public abstract State getState();
}
