package com.stream_work.ch05.api;

/**
 * This Source class is the base class for all user defined sources.
 */
public abstract class StatefulSource extends Component {
  private static final long serialVersionUID = -1605403601010395103L;

  public StatefulSource(String name, int parallelism) {
    super(name, parallelism);
  }

  /**
   * Set up instance.
   * @param instance The instance id (an index starting from 0) of this source instance.
   * @param state The state object used to construct the instance.
   */
  public abstract void setupInstance(int instance, State state);

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  public abstract void getEvents(EventCollector eventCollector);

  /**
   * Get the current state of the component. The state object can be used later
   * to reconstruct the component if a job recovery or rollback happens.
   * @return the current state of the component.
   */
  public abstract State getState();
}
