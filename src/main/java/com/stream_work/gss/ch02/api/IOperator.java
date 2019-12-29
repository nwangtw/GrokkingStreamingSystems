package com.gss.ch02.api;

import java.util.List;

/**
 * The interface for the operators.
 * An operation reads events from the incoming stream, apply logic on them,
 * and emits result to the outgoing stream.
 */
public interface IOperator extends IComponent {
    /**
     * Apply logic to the incoming event and generate results.
     * The function is abstract and needs to be implemented by users.
     * @param event The incoming event
     * @param eventCollector The outgoing event collector
     */
    void apply(Event event, List<Event> eventCollector);
}
