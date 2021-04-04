package com.streamwork.ch05.api;

/**
 * This Operator class is the base class for all user defined operators.
 */
public abstract class StatefulOperator extends Component {
  private static final long serialVersionUID = -1339819357989957259L;

    // Grouping strategy for the incoming data
  private final GroupingStrategy grouping;

  public StatefulOperator(String name, int parallelism) {
    super(name, parallelism);
    this.grouping = new ShuffleGrouping();  // Default
  }

  public StatefulOperator(String name, int parallelism, GroupingStrategy grouping) {
    super(name, parallelism);
    this.grouping = grouping;
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   * @param state The state object used to construct the instance.
   */
  public abstract void setupInstance(int instance, State state);

  /**
   * Apply logic to the incoming event and generate results.
   * The function is abstract and needs to be implemented by users.
   * @param event The incoming event
   * @param eventCollector The outgoing event collector
   */
  public abstract void apply(Event event, EventCollector eventCollector);

  /**
   * Get the current state of the component. The state object can be used later
   * to reconstruct the component if a job recovery or rollback happens.
   * @return the current state of the component.
   */
  public abstract State getState();

  /**
   * Get the grouping key of an event.
   * @return The grouping strategy of this operator
   */
  public GroupingStrategy getGroupingStrategy() {
    return grouping;
  }
}
