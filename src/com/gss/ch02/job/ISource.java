package com.gss.ch02.job;

/**
 * The interface for the sources.
 * A source accepts events from the outside world and emits into the system
 * via the outgoing stream,
 * @param <O> The data type of the events in the outgoing stream
 */
public interface ISource<O> extends IComponent<O> {
}
