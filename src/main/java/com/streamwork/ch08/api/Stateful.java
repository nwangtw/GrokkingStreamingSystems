package com.streamwork.ch08.api;

/**
 * This Stateful interface for all stateful components.
 * Code example:
 *   class MySource extends Source implements Stateful {
 *     ....
 *   }
 */
public interface Stateful {
  /**
   * Set up instance from a state object.
   * @param instance The instance id (an index starting from 0) of this source instance.
   */
  void setupInstanceFromState(State state);

  /**
   * Accept events from external into the system.
   * The function is abstract and needs to be implemented by users.
   * @param eventCollector The outgoing event collector
   */
  State getState();
}
