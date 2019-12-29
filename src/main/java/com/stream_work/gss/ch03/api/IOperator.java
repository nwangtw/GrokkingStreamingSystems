package com.gss.ch03.api;

import java.util.List;

/**
 * The interface for the operators.
 * An operation reads events from the incoming stream, apply logic on them,
 * and emits result to the outgoing stream.
 */
public interface IOperator extends IComponent {
    /**
     * Set up instance.
     * @param instance The instance id (an index starting from 0) of this source instance.
     */
    void setupInstance(int instance);

    /**
     * Apply logic to the incoming event and generate results.
     * The function is abstract and needs to be implemented by users.
\     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    void apply(Event event, List<Event> eventCollector);
}
