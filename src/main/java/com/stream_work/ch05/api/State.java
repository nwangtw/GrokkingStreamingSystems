package com.stream_work.ch05.api;

import java.io.Serializable;

/**
 * This is the base class for all the state classes.
 * Users should extend this class to implement all their own state classes.
 */
public abstract class State implements Serializable {
    private static final long serialVersionUID = -5061846867537863454L;

    /**
     * Get data stored in the state. The data object should be casted to more
     * specific type afterwards. Note that the data object needs to be serializable.
     *
     * @return The data object stored in the state.
     */
  public abstract Serializable getData();
}
